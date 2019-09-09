package bridge.jsf;

import bridge.entity.Sharing;
import bridge.jsf.util.JsfUtil;
import bridge.jsf.util.PaginationHelper;
import bridge.ejb.SharingFacade;
import bridge.entity.User;
import bridge.web.CurrentUser;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.ejb.Stateless;

@Named
@SessionScoped
@Stateless
public class SharingController implements Serializable {

    private Sharing current;
    private int sort = 0;
    private DataModel items = null;
    @EJB
    private bridge.ejb.SharingFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private User loginUser;

    public SharingController() {
    }

    public Sharing getSelected() {
        if (current == null) {
            current = new Sharing();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SharingFacade getFacade() {
        return ejbFacade;
    }

    public void addUp() {
        current.getUserCollection().add(this.loginUser);
        current.setSUp(current.getSUp() + 1);
        ejbFacade.updateUP(current);

    }

    public List<Sharing> getByUid(User user) {
        List<Sharing> lists = ejbFacade.getByUid(user);
        return lists;

    }

//    public DataModel getItemsBySort() {
//        if (sort == 0) {
//            return getItemsByDateDesc();
//        } else {
//            return getItemsByUpDesc();
//        }
//    }
    public DataModel getItemsByUpDesc() {
        pagination = new PaginationHelper(10) {
            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                return ejbFacade.createPageDataModelByUpDesc();
            }
        };

        items = pagination.createPageDataModel();
        return items;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }

                public DataModel createPageOrderByDate() {
                    return ejbFacade.createPageDataModelByDateDesc();
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Sharing) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String goSharing() {
        int id = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("sharing_id"));
        current = getFacade().find(id);
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/sharing/View.xhtml";
    }

    public String prepareCreate() {
        current = new Sharing();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            if (getUser() == 1) {
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SharingCreated"));
                return "View";
            } else {
                return "/login.xhtml";
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public int getUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserController userbean = (UserController) context.getApplication().createValueBinding("#{userController}").getValue(context);
        if (userbean == null) {
            return 0;
        } else {
            this.loginUser = userbean.getLoginUser();
            current.setUserUId(this.loginUser);
            return 1;
        }
    }

    public String prepareEdit() {
        current = (Sharing) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            if (getUser() == 1) {
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SharingUpdated"));
                return "View";
            } else {
                return "/login.xhtml";
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String triggerList() {
        return "/sharing/List.xhtml";
    }

    public String destroy() {
        current = (Sharing) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "List";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SharingDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public void removeUp() {
        current.getUserCollection().remove(this.loginUser);
        current.setSUp(current.getSUp() - 1);
        ejbFacade.updateUP(current);
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Sharing getSharing(java.lang.Integer id) {
        return ejbFacade.find(id);

    }

    @FacesConverter(forClass = Sharing.class)
    public static class SharingControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SharingController controller = (SharingController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sharingController");
            return controller.getSharing(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Sharing) {
                Sharing o = (Sharing) object;
                return getStringKey(o.getSId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Sharing.class.getName());
            }
        }

    }

    public String getItemsByDateDesc() {

        pagination = new PaginationHelper(10) {
            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                return ejbFacade.createPageDataModelByDateDesc();
            }
        };
        items = pagination.createPageDataModel();

        return "/sharing/List.xhtml";
    }

    public Sharing getCurrent() {
        return current;
    }

    public void setCurrent(Sharing current) {
        this.current = current;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void sortItems(int sort) {
        if (sort == 0) {
            getItemsByDateDesc();
        } else {
            getItemsByUpDesc();
        }
    }

    public int showUp() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserController userbean = (UserController) context.getApplication().createValueBinding("#{userController}").getValue(context);
        this.loginUser = userbean.getLoginUser();
        int Status = ejbFacade.getUpSpecific(this.loginUser.getUId(), current.getSId());
        return Status;
    }

    public String getItemsBySharingSearch_user(String key) {
        pagination = new PaginationHelper(10) {
            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                return ejbFacade.createPageDataModelBySharingSearch_user(key);
            }
        };
        items = pagination.createPageDataModel();
        FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
        return "/sharing/List.xhtml";
    }

    public String refresh_user() {
        getItemsBySharingSearch_user("");
        return "/sharing/List.xhtml";
    }

    public String getItemsBySharingSearch() {
        pagination = new PaginationHelper(10) {
            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                return ejbFacade.createPageDataModelBySharingSearch();
            }
        };
        items = pagination.createPageDataModel();
        FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
        return "List";
    }

    public String refresh() {
        items = null;
        return "List";
    }

}
