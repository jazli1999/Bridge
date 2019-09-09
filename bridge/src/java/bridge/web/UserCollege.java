/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import bridge.ejb.CollegeInfoBean;
import bridge.ejb.UserInfoBean;
import bridge.entity.College;
import bridge.entity.User;
import com.sun.xml.ws.security.addressing.impl.policy.Constants;
import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jasmi
 */
@Named
@SessionScoped
public class UserCollege implements Serializable {

    private static final Logger logger = Logger.getLogger("bridge.web.UserCollege");

    @EJB
    private CollegeInfoBean collegeInfoBean;
    @EJB
    private UserInfoBean userInfoBean;

    protected int cId;
    protected int uId;
    protected String bookmarkStatus;

    public String userStarsCollege() {
        CurrentUser current_user = (CurrentUser) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("currentUser");
        if (current_user.getLoginStatus() != 1) {
            return "/login.xhtml";
        }

        this.cId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("cur_college_id"));
        this.uId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("cur_user_id"));

        College college = collegeInfoBean.getCollegeById(this.cId);
        User user = userInfoBean.getUserById(this.uId);
        college.getUserCollection().add(user);

        if (collegeInfoBean.addStars(college)) {
            bookmarkStatus = "Bookmarked!";
        } else {
            bookmarkStatus = "Bookmark failed!";
        }

        CollegeInfo collegeInfo = (CollegeInfo) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("collegeInfo");
        collegeInfo.refreshStars();
        collegeInfo.setIsBookmarked(true);
        return "/college/collegeInfoPage.xhtml";
    }

    public String userUnstarsCollege() {
        CurrentUser current_user = (CurrentUser) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("currentUser");
        if (current_user.getLoginStatus() != 1) {
            return "/login.xhtml";
        }

        this.cId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("cur_college_id"));
        this.uId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("cur_user_id"));

        College college = collegeInfoBean.getCollegeById(this.cId);
        User user = userInfoBean.getUserById(this.uId);
        college.getUserCollection().remove(user);

        if (collegeInfoBean.removeStars(college)) {
            bookmarkStatus = "Unbookmarked!";
        } else {
            bookmarkStatus = "Unbookmark failed!";
        }

        CollegeInfo collegeInfo = (CollegeInfo) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("collegeInfo");
        collegeInfo.setIsBookmarked(false);
        collegeInfo.refreshStars();

        return "/college/collegeInfoPage.xhtml";
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

}
