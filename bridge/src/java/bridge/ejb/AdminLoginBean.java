package bridge.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.NoResultException;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
@Stateless
public class AdminLoginBean implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public String getResult(String ad_name, String ad_password) {
        Query query = em.createNamedQuery("CheckPassword");
        query.setParameter("aName", ad_name);
        try {
            String password = (String) query.getSingleResult();
            if (password.equals(ad_password)) {

                return "/college_ad/List.xhtml";
            }
        } catch (NoResultException e) {
            return "false";
        }
        return "false";
    }
}
