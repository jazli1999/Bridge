/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.ejb;

import bridge.entity.College;
import bridge.entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/*
 *
 * @author Jasmi
 */
@Stateless
public class UserInfoBean {

    private static final Logger logger = Logger.getLogger("bridge.ejb.UserInfoBean");

    @PersistenceContext
    private EntityManager em;

    public UserInfoBean() {
    }

    public User validateUser(String username) {
        Query query = em.createNamedQuery("User.findByUUsername");
        query.setParameter("uUsername", username);
        List result = query.getResultList();
        User user = null;
        if (result.size() > 0) {
            user = (User) result.get(0);
        }
        return user;
    }

    public User getUserById(int id) {
        Query query = em.createNamedQuery("User.findByUId");
        query.setParameter("uId", id);
        User user = (User) query.getResultList().get(0);
        return user;
    }

    // Show all the colleges starred by the current user
    public List<College> getAllStarredColleges(User user) {
        Query query = em.createNamedQuery("User.findAllStarred");
        query.setParameter("uId", user.getUId());

        List<College> result = query.getResultList();

        return result;
    }

    public boolean addStarColleges(int cId, int uId) {
        try {
            Query query = em.createNamedQuery("College.addStars");
            query.setParameter("cId", cId);
            query.setParameter("uId", uId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean getIsUsernameTaken(String username) {
        logger.log(Level.INFO, "Running?");
        Query query = em.createNamedQuery("User.findByUUsername");
        query.setParameter("uUsername", username);
        List result = query.getResultList();
        if (result.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public int getMaxId() {
        int maxId = (Integer) em.createNamedQuery("User.findMaxId").getResultList().get(0);
        return maxId;
    }
    
    public void saveUser(User user) {
        em.persist(user);
    }
}
