/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.ejb;

import bridge.entity.Sharing;
import bridge.entity.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Stardust
 */
@Named
@Stateless
public class SharingFacade extends AbstractFacade<Sharing> {

    String keyword;

    @PersistenceContext
    private EntityManager em;

    private User currentUser;
    private Sharing currentSharing;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SharingFacade() {
        super(Sharing.class);
    }

    public DataModel createPageDataModelByDateDesc() {
        List<Sharing> lists = em.createNamedQuery("Sharing.orderByDate").getResultList();
        return new ListDataModel(lists);
    }

    public DataModel createPageDataModelByUpDesc() {
        List<Sharing> lists = em.createNamedQuery("Sharing.orderByUp").getResultList();
        return new ListDataModel(lists);
    }

    public int getUpSpecific(int uid, int sid) {
        Query query = em.createNamedQuery("userupsharing.findSpecific");
        query.setParameter("sid", sid);
        query.setParameter("uid", uid);
        List lists = query.getResultList();
        if (lists != null && lists.size() > 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void updateUP(Sharing sharing) {
        em.merge(sharing);
        em.flush();
    }

    public DataModel createPageDataModelBySharingSearch() {
        Query query = em.createNamedQuery("Sharing.findBySearch");
        keyword = "%" + keyword + "%";
        List<Sharing> lists = query.setParameter("keyWord", keyword).getResultList();
        keyword = "";
        return new ListDataModel(lists);
    }

    public DataModel createPageDataModelBySharingSearch_user(String key) {
        Query query = em.createNamedQuery("Sharing.findBySearch");
        key = "%" + key + "%";
        List<Sharing> lists = query.setParameter("keyWord", key).getResultList();
        key = "";
        return new ListDataModel(lists);
    }
    
    public List<Sharing> getByUid(User user){
        Query query=em.createNamedQuery("Sharing.findbyUid");
        query.setParameter("sUid",user);
        List<Sharing> lists=query.getResultList();
        return lists;
    }


}
