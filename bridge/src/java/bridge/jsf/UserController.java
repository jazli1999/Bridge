package bridge.jsf;

import bridge.entity.User;
import bridge.jsf.util.JsfUtil;
import bridge.jsf.util.PaginationHelper;
import bridge.ejb.UserFacade;
import bridge.ejb.UserInfoBean;
import bridge.entity.College;
import bridge.entity.Sharing;
import bridge.exception.UserNotFoundException;
import bridge.web.CollegeInfo;
import bridge.web.CurrentUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.validation.constraints.NotNull;

@Named
@SessionScoped
public class UserController implements Serializable {

    private User current;
    private DataModel items = null;

    @EJB
    private bridge.ejb.UserFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    @EJB
    private UserInfoBean userInfoBean;
    @EJB
    private SharingController sharingController;

    //backingbean
    @NotNull
    protected Integer uId;
    protected String uUsername;
    protected String uPassword;
    protected College collegeCId;
    protected int u_state;
    protected String uAvatar;

    //can be null    
    protected String uName;
    protected Long uGPA;
    protected Long uTofel;
    protected Long uGre;
    protected String uWechat;
    protected String uEmail;
    protected String uInfo;
    protected String uMajor;

    protected List<College> starred;
    protected List<Sharing> lists;

    protected Collection<College> collegeCollection;
    protected Collection<Sharing> sharingCollection;
    private static final Logger logger = Logger.getLogger("bridge.web.UserController");

    protected int userId;
    protected String username;
    protected String password;

    protected User loginUser;
    protected int loginStatus;

    private CurrentUser currentUser;

    public UserController() {
        currentUser = (CurrentUser) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("currentUser");

        this.loginStatus = currentUser.getLoginStatus();
        this.loginUser = currentUser.getCurUser();
    }

    public String logOut() {
        currentUser = (CurrentUser) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("currentUser");

        currentUser.logOut();
        this.loginStatus = currentUser.getLoginStatus();
        this.loginUser = currentUser.getCurUser();
        
        return "/login.xhtml";
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String validateUserLogin() {
        User attemptUser = ejbFacade.validateUser(this.username);
        if (attemptUser != null) {
            if (password.equals(attemptUser.getUPassword())) {
                this.userId = attemptUser.getUId();
                this.loginStatus = 1;
                this.current = attemptUser;
                return "/index.xhtml";
            } else {
                this.loginStatus = 2;
                return "/login.xhtml";
            }
        } else {
            this.loginStatus = 3;
            return "/index.html";
        }
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserFacade getFacade() {
        return ejbFacade;
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
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/User/userInfoPage";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            setRealTimeUser();
            return "/User/userInfoPage";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
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
            return "/User/userInfoPage";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
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

    public User getUser(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
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
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getUId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + User.class.getName());
            }
        }
    }

    public String getUserInfoPage() {
        CurrentUser current_user = (CurrentUser) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("currentUser");
        if (current_user.getLoginStatus() != 1) {
            return "/login.xhtml";
        }

        this.uId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("user_id"));
        logger.log(Level.INFO, "user_id: {0}", String.valueOf(this.uId));
        User user = setRealTimeUser();
        logger.log(Level.INFO, "user_name: {0}", user.getUUsername());
        getAllStarred(user);
        getAllLists(user);
        return "/User/userInfoPage.xhtml";
    }

    public User setRealTimeUser() {
        User user = userInfoBean.getUserById(this.uId);

        this.uUsername = user.getUUsername();
        this.uName = user.getUName();

        this.uEmail = user.getUEmail();
        this.uWechat = user.getUWechat();
        this.uInfo = user.getUInfo();
        this.uMajor = user.getUMajor();
        this.u_state = user.getUState();
        this.collegeCId = user.getCollegeCId();
        this.uAvatar = user.getUAvatar();

        this.uTofel = user.getUTofel() != null ? user.getUTofel() : 0;
        this.uGre = user.getUGre() != null ? user.getUGre() : 0;
        this.uGPA = user.getUGPA() != null ? user.getUGPA() : 0;

        return user;
    }
    
    
    public List<Sharing> getLists() {
        return lists;
    }

    public void setLists(List<Sharing> lists) {
        this.lists = lists;
    }


    public void getAllStarred(User user) {
        this.starred = userInfoBean.getAllStarredColleges(user);
    }
    
    public void getAllLists(User user){
        this.lists = sharingController.getByUid(user);
    }

    public User login(Integer uId) throws UserNotFoundException {
        this.current = getFacade().findUserById(uId);
        return current;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public String getUUsername() {
        return uUsername;
    }

    public void setUUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public College getCollegeCId() {
        return collegeCId;
    }

    public void setCollegeCId(College collegeCId) {
        this.collegeCId = collegeCId;
    }

    public int getU_state() {
        return u_state;
    }

    public void setU_state(int u_state) {
        this.u_state = u_state;
    }

    public String getUAvatar() {
        return uAvatar;
    }

    public void setUAvatar(String uAvatar) {
        this.uAvatar = uAvatar;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public Long getUGPA() {
        return uGPA;
    }

    public void setUGPA(Long uGPA) {
        this.uGPA = uGPA;
    }

    public Long getUTofel() {
        return uTofel;
    }

    public void setUTofel(Long uTofel) {
        this.uTofel = uTofel;
    }

    public Long getUGre() {
        return uGre;
    }

    public void setUGre(Long uGre) {
        this.uGre = uGre;
    }

    public String getUWechat() {
        return uWechat;
    }

    public void setUWechat(String uWechat) {
        this.uWechat = uWechat;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getUInfo() {
        return uInfo;
    }

    public void setUInfo(String uInfo) {
        this.uInfo = uInfo;
    }

    public String getUMajor() {
        return uMajor;
    }

    public void setUMajor(String uMajor) {
        this.uMajor = uMajor;
    }

    public Collection<College> getCollegeCollection() {
        return collegeCollection;
    }

    public void setCollegeCollection(Collection<College> collegeCollection) {
        this.collegeCollection = collegeCollection;
    }

    public Collection<Sharing> getSharingCollection() {
        return sharingCollection;
    }

    public void setSharingCollection(Collection<Sharing> sharingCollection) {
        this.sharingCollection = sharingCollection;
    }

    public List<College> getStarred() {
        return starred;
    }

    public void setStarred(List<College> starred) {
        this.starred = starred;
    }

    public String getItemsByUserSearch() {
        pagination = new PaginationHelper(10) {
            @Override
            public int getItemsCount() {
                return getFacade().count();
            }

            @Override
            public DataModel createPageDataModel() {
                return ejbFacade.createPageDataModelByUserSearch();
            }
        };
        items = pagination.createPageDataModel();

        return "List";
    }

    public String refresh() {
        items = null;
        return "List";
    }

    public String save() {
        try {
            current.setUGPA(this.uGPA);
            current.setUTofel(this.uTofel);
            current.setUGre(this.uGre);
            current.setUWechat(this.uWechat);
            current.setUInfo(this.uInfo);
            current.setUMajor(this.uMajor);
            current.setUState(this.u_state);
            current.setCollegeCId(this.collegeCId);
            getFacade().edit(current);
            logger.log(Level.INFO, "edit");
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            setRealTimeUser();
            return "/User/userInfoPage";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurs return null");
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEditPage() {
        //current = (User) getItems().getRowData();

        this.uId = loginUser.getUId();
        logger.log(Level.INFO, "user_id: {0}", String.valueOf(this.uId));
        User user = setRealTimeUser();
        current = user;
        // selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/User/EditUser";
    }
}
