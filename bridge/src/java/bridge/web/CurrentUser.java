/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.web;

import bridge.ejb.UserInfoBean;
import bridge.entity.User;
import bridge.jsf.UserController;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import bridge.ejb.CollegeInfoBean;
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
public class CurrentUser implements Serializable {

    @EJB
    private UserInfoBean userInfoBean;
    @EJB
    private CollegeInfoBean collegeInfoBean;

    protected int userId;
    protected String username;
    protected String password;
    protected int loginStatus = 0;

    private User curUser;

    private static final Logger logger = Logger.getLogger("bridge.web.CurrentUser");

    /*
    0: haven't logged in
    1: successfully logged in
    2: wrong password
    3: user does not exist
    4: username already exists
     */
    public CurrentUser() {
    }

    public String validateUserLogin() {
        User attemptUser = userInfoBean.validateUser(this.username);
        this.curUser = attemptUser;
        String enPasswd = md5Encrypt(this.password);
        logger.log(Level.INFO, "loginStatus {0}", this.loginStatus);

        if (attemptUser != null) {
            if (enPasswd.equals(attemptUser.getUPassword())) {
                this.userId = attemptUser.getUId();
                this.loginStatus = 1;
                logger.log(Level.INFO, "loginStatus {0}", this.loginStatus);
                return "/index.xhtml";
            } else {
                this.loginStatus = 2;
                logger.log(Level.INFO, "loginStatus {0}", this.loginStatus);
                this.username = null;
                this.password = null;
                this.userId = 0;
                curUser = null;
                FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
                return "/login.xhtml";
            }
        } else {
            this.loginStatus = 3;
            logger.log(Level.INFO, "loginStatus {0}", this.loginStatus);
            this.username = null;
            this.password = null;
            this.userId = 0;
            curUser = null;
            FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
            return "/login.xhtml";
        }
    }

    public String createUser() {
        // empty -> true
        if (!userInfoBean.getIsUsernameTaken(this.username)) {
            this.loginStatus = 4;
            FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
            return "/login.xhtml";
        }
        logger.log(Level.INFO, "running?");
        String encryptedPassword = md5Encrypt(this.password);
        User newUser = new User();
        newUser.setUUsername(this.username);
        newUser.setUPassword(encryptedPassword);
        newUser.setUId(userInfoBean.getMaxId() + 1);
        newUser.setUAvatar("/resources/avatar/uid_3.png");
        newUser.setUState(0);

        newUser.setCollegeCId(collegeInfoBean.getCollegeById(0));

        userInfoBean.saveUser(newUser);

        return validateUserLogin();
    }

    public String md5Encrypt(String rawPasswd) {
        String encrypted = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = rawPasswd.getBytes();
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            encrypted = hashtext;

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encrypted;
    }

    public String logOut() {
        curUser = null;
        userId = 0;
        username = null;
        password = null;
        loginStatus = 0;
        FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
        return "/login.xhtml";
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

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
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

}
