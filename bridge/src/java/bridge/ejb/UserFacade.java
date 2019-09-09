/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.ejb;

import bridge.entity.User;
import bridge.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.NamedArg;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Root;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
/**
 *
 * @author Stardust
 */
@SessionScoped
@Named
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext
    private EntityManager em;
    
    private static final Logger logger = Logger.getLogger("bridge.ejb.UserFacade");

    private Class<User> entityClass;
     String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
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

    //register into database
    public void createUser(User entity) {

        try {
            getEntityManager().persist(entity);
            logger.log(Level.INFO, "Persisted User {0}", entity);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    //edit changes into database
    public void editUser(User entity) {

        try {
            getEntityManager().merge(entity);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    //delete the user from db
    public void remove(User entity) {

        try {
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }

    }

    //find user by pk uid from the db
    public User findUserById(Integer pk) throws UserNotFoundException {

        try {

            return getEntityManager().find(User.class, pk);

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    //select user by uUsername from the db
    public User findUserByuUsername(String un) throws UserNotFoundException {

        try {
            Query query = getEntityManager().createNamedQuery("User.findByUUsername");
            query.setParameter("uUsername", un);
            List result = query.getResultList();
            User user = null;
            if (result.size() > 0) {
                user = (User) result.get(0);
                return user;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    //select user by  uName, uGPA,uTofel,uGre,uWechat,uEmail, uInfo,uMajor,uState,collegeCId from the db
    public User findUserByuName(String un) throws UserNotFoundException {

        try {
            Query query = getEntityManager().createNamedQuery("User.findByUName");
            query.setParameter("uName", un);
            List result = query.getResultList();
            User user = null;
            if (result.size() > 0) {
                user = (User) result.get(0);
            }
            if (user != null) {
                return user;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    //select user by uGPA from the db
    public List<User> findUserByuGPA(Long un) throws UserNotFoundException {

        try {
            List<User> reList = new ArrayList();

            Query query = getEntityManager().createNamedQuery("User.findByUGPA");
            query.setParameter("uGPA", un);
            List result = query.getResultList();
            User user = null;
            for (int i = 0; i < result.size(); i++) {
                user = (User) result.get(i);
                reList.add(user);
            }
            if (reList != null) {
                return reList;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }

    }

    //select user by uTofel from the db
    public List<User> findUserByuTofel(Long un) throws UserNotFoundException {

        try {
            List<User> reList = new ArrayList();
            Query query = getEntityManager().createNamedQuery("User.findByUTofel");
            query.setParameter("uTofel", un);

            List result = query.getResultList();
            User user = null;
            for (int i = 0; i < result.size(); i++) {
                user = (User) result.get(i);
                reList.add(user);
            }
            if (reList != null) {
                return reList;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }

    }

    //select user by uGre from the db
    public List<User> findUserByuGre(Long un) throws UserNotFoundException {

        try {
            List<User> reList = new ArrayList();
            Query query = getEntityManager().createNamedQuery("User.findByUGre");
            query.setParameter("uGre", un);
            List result = query.getResultList();
            User user = null;
            for (int i = 0; i < result.size(); i++) {
                user = (User) result.get(i);
                reList.add(user);
            }
            if (reList != null) {
                return reList;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }

    }

    //select user by uWechat,uEmail, uInfo,uMajor,uState,collegeCId from the db
    public User findUserByuWechat(String wechat) throws UserNotFoundException {

        try {
            Query query = getEntityManager().createNamedQuery("User.findByUWechat");
            query.setParameter("uWechat", wechat);
            List result = query.getResultList();
            User user = null;
            if (result.size() > 0) {
                user = (User) result.get(0);
            }
            return user;

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    //select user by uEmail from the db
    public User findUserByuEmail(String email) throws UserNotFoundException {

        try {
            Query query = getEntityManager().createNamedQuery("User.findByUEmail");
            query.setParameter("uEmail", email);
            List result = query.getResultList();
            User user = null;
            if (result.size() > 0) {
                user = (User) result.get(0);
            }
            return user;

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    //select user by uMajor from the db
    public List<User> findUserByuMajor(String major) throws UserNotFoundException {

        try {
            List<User> reList = new ArrayList();
            Query query = getEntityManager().createNamedQuery("User.findByUMajor");
            query.setParameter("uMajor", major);
            List result = query.getResultList();
            User user = null;
            for (int i = 0; i < result.size(); i++) {
                user = (User) result.get(i);
                reList.add(user);
            }
            if (reList != null) {
                return reList;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    //select user by uState from the db
    public List<User> findUserByuuState(int state) throws UserNotFoundException {

        try {
            List<User> reList = new ArrayList();
            Query query = getEntityManager().createNamedQuery("User.findByUState");
            query.setParameter("uState", state);
            List result = query.getResultList();
            User user = null;
            for (int i = 0; i < result.size(); i++) {
                user = (User) result.get(i);
                reList.add(user);
            }
            if (reList != null) {
                return reList;
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }
    
 public List<User> searchByUser(String keyword) {

        Query query = em.createNamedQuery("User.findBySearch");

        keyword = "%" + keyword + "%";
        query.setParameter("keyWord", keyword);
        List<User> results3 = query.getResultList();

        return results3;

    }
      public DataModel createPageDataModelByUserSearch() {
        Query query = em.createNamedQuery("User.findBySearch");
        keyword = "%" + keyword + "%";
        List<User> lists = query.setParameter("keyWord", keyword).getResultList();
        keyword = "";
        return new ListDataModel(lists);
    }

}
