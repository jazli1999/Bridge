/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.ejb;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.faces.model.DataModel;
import bridge.entity.College;
import bridge.entity.Sharing;
import bridge.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named
@SessionScoped
@Stateless
public class SearchBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    private List<Sharing> results1 = null;
    private List<College> results2 = null;
    private List<User> results3 = null;

    public List<Sharing> searchBy1(String keyword) {

        Query query = em.createNamedQuery("Sharing.findBySearch");
        keyword = "%" + keyword + "%";
        query.setParameter("keyWord", keyword);
        results1 = query.getResultList();

        return results1;

    }

    public List<College> searchBy2(String keyword) {

        Query query = em.createNamedQuery("College.findBySearch");
        keyword = "%" + keyword + "%";
        query.setParameter("keyWord", keyword);
        results2 = query.getResultList();

        return results2;

    }

    public List<User> searchBy3(String keyword) {

        Query query = em.createNamedQuery("User.findBySearch");

        keyword = "%" + keyword + "%";
        query.setParameter("keyWord", keyword);
        results3 = query.getResultList();

        return results3;

    }
}
