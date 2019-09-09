/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.ejb;

import javax.inject.Named;
import bridge.entity.College;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
@Stateless
public class CollegeFacade extends AbstractFacade<College> {

    @PersistenceContext
    private EntityManager em;

    protected String keyword;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CollegeFacade() {
        super(College.class);
    }

//    public DataModel createPageDataModelByCollegeSearch() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public DataModel createPageDataModelByCollegeSearch() {
        Query query = em.createNamedQuery("College.findBySearch");
        this.keyword = "%" + keyword + "%";
        List<College> lists = query.setParameter("keyWord", keyword).getResultList();
        keyword = "";
        return new ListDataModel(lists);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
