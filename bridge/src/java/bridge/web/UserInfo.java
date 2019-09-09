/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.web;

import bridge.ejb.UserInfoBean;
import bridge.entity.College;
import bridge.entity.User;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Jasmi
 */
@Named
@SessionScoped
public class UserInfo implements Serializable {

    private static final Logger logger = Logger.getLogger("bridge.web.UserInfo");
    
    @EJB
    private UserInfoBean userInfoBean;

    protected int userId;
    protected List<College> starred;

    public UserInfo() {
    }

    public String getUserInfoPage() {
        this.userId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("user_id"));
        logger.log(Level.INFO, "user_id: {0}", String.valueOf(this.userId));
        User user = userInfoBean.getUserById(this.userId);

        logger.log(Level.INFO, "user_name: {0}", user.getUUsername());
        getAllStarred(user);
        return "/User/userInfoPage.xhtml";
    }
    
    public void getAllStarred(User user) {
        this.starred = userInfoBean.getAllStarredColleges(user);
    }
    
    public boolean getIsUsernameExists(String username) {
        return userInfoBean.getIsUsernameTaken(username);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public List<College> getStarred() {
        return starred;
    }

    public void setStarred(List<College> starred) {
        this.starred = starred;
    }

}
