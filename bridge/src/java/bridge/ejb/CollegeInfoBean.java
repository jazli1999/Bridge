/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.ejb;

import bridge.entity.College;
import bridge.entity.User;
import bridge.entity.CollegeShow;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jasmi
 */
@Stateless
public class CollegeInfoBean {

    private static final Logger logger = Logger.getLogger("bridge.web.CollegeInfoBean");

    @PersistenceContext
    private EntityManager em;

    protected int college_id;

    public CollegeInfoBean() {
    }

    // Show college details
    public College getCollegeById(int id) {
        Query query = em.createNamedQuery("College.findByCId");
        query.setParameter("cId", id);
        College college = (College) query.getResultList().get(0);
        return college;
    }

    // Show all the starers of the current school
    public List<User> getAllStarers(College college) {
        logger.log(Level.INFO, "Bean got college {0}", String.valueOf(college.getCId()));
        Query query = em.createNamedQuery("College.findAllStars");
        query.setParameter("cId", college.getCId());

        List<User> result = query.getResultList();

        return result;
    }

    public List processInfo_default() {
        List<College> c = em.createNamedQuery("College.findAll").getResultList();
        List<CollegeShow> c_Info = new ArrayList<>();//装大学的信息和图片
        String cInfo;//中间变量

        for (int i = 0; i < c.size(); i++) {
            if (c.get(i).getCGre() == null && c.get(i).getCTofel() == null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + "no content" + "<br>"
                        + "GRE:" + "&nbsp;" + "no content" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";
            } else if (c.get(i).getCGre() == null && c.get(i).getCTofel() != null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + c.get(i).getCTofel() + "分以上" + "<br>"
                        + "GRE:" + "&nbsp;" + "no content" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";
            } else if (c.get(i).getCGre() != null && c.get(i).getCTofel() == null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + "no content" + "<br>"
                        + "GRE:" + "&nbsp;" + c.get(i).getCGre() + "分以上" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";

            } else {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + c.get(i).getCTofel() + "分以上" + "<br>"
                        + "GRE:" + "&nbsp;" + c.get(i).getCGre() + "分以上" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";

            }
            CollegeShow collegeShow = new CollegeShow();

            collegeShow.setIntroduction(cInfo);
            collegeShow.setPic(c.get(i).getCPic());
            logger.log(Level.INFO, "Pic path: {0}", collegeShow.getPic());
            collegeShow.setCid(c.get(i).getCId());

            c_Info.add(collegeShow);

            if (collegeShow.getCid() == 0) {
                c_Info.remove(collegeShow);//如果cid为0,移除该元素
            }
        }
        return c_Info;
    }

    public List processInfo_feeAsc() {
        //存放College实体对象的列表，有所有的属性
        List<College> c = em.createNamedQuery("College.CFeeAsc").getResultList();
        List<CollegeShow> c_Info = new ArrayList<>();//存放大学的信息和图片
        String cInfo;//中间变量

//循环将信息存入CollegeShow,再将存入信息的collegeShow对象存入c_info中
        for (int i = 0; i < c.size(); i++) {
            if (c.get(i).getCGre() == null && c.get(i).getCTofel() == null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + "no content" + "<br>"
                        + "GRE:" + "&nbsp;" + "no content" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";
            } else if (c.get(i).getCGre() == null && c.get(i).getCTofel() != null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + c.get(i).getCTofel() + "分以上" + "<br>"
                        + "GRE:" + "&nbsp;" + "no content" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";
            } else if (c.get(i).getCGre() != null && c.get(i).getCTofel() == null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + "no content" + "<br>"
                        + "GRE:" + "&nbsp;" + c.get(i).getCGre() + "分以上" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";

            } else {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + c.get(i).getCTofel() + "分以上" + "<br>"
                        + "GRE:" + "&nbsp;" + c.get(i).getCGre() + "分以上" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";

            }
            CollegeShow collegeShow = new CollegeShow();

            collegeShow.setPic(c.get(i).getCPic());
            collegeShow.setIntroduction(cInfo);
            collegeShow.setCid(c.get(i).getCId());

            c_Info.add(collegeShow);

            if (collegeShow.getCid() == 0) {
                c_Info.remove(collegeShow);//如果cid为0,移除该元素
            }
        }
        return c_Info;
    }

    public static String getFacesParamValue(String name) {
        logger.log(Level.INFO, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name));
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    public boolean addStars(College college) {
        try {
            em.merge(college);
            em.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean getIsBookmarked(int uId, int cId) {
        Query query = em.createNamedQuery("College.getStarStatus");
        query.setParameter("uId", uId);
        query.setParameter("cId", cId);
        List result = query.getResultList();
        
        return (!result.isEmpty());
    }
    
    public boolean removeStars(College college) {
        try {
            em.merge(college);
            em.flush();
            return true;
        } catch(Exception e) {
            return false;
        }
    }
      public List processInfo_search(String keyword) {
         List<College> c=null;
        List<CollegeShow> c_Info = new ArrayList<>();//装大学的信息和图片
        String cInfo;//中间变量
        Query query = em.createNamedQuery("College.findBySearch");
        keyword = "%" + keyword + "%";
        query.setParameter("keyWord", keyword);
       c = query.getResultList();

        for (int i = 0; i < c.size(); i++) {
            if (c.get(i).getCGre() == null && c.get(i).getCTofel() == null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + "no content" + "<br>"
                        + "GRE:" + "&nbsp;" + "no content" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";
            } else if (c.get(i).getCGre() == null && c.get(i).getCTofel() != null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + c.get(i).getCTofel() + "分以上" + "<br>"
                        + "GRE:" + "&nbsp;" + "no content" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";
            } else if (c.get(i).getCGre() != null && c.get(i).getCTofel() == null) {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + "no content" + "<br>"
                        + "GRE:" + "&nbsp;" + c.get(i).getCGre() + "分以上" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";

            } else {
                cInfo = c.get(i).getCNameCh() + "(" + c.get(i).getCNameEn() + ")" + "<br>"
                        + "Tofel:" + "&nbsp;" + c.get(i).getCTofel() + "分以上" + "<br>"
                        + "GRE:" + "&nbsp;" + c.get(i).getCGre() + "分以上" + "<br>"
                        + "学费:" + "&nbsp;" + c.get(i).getCFee() + "美元/年" + "<br>";

            }
            CollegeShow collegeShow = new CollegeShow();

            collegeShow.setIntroduction(cInfo);
            collegeShow.setPic(c.get(i).getCPic());
            collegeShow.setCid(c.get(i).getCId());

            c_Info.add(collegeShow);

            if (collegeShow.getCid() == 0) {
                c_Info.remove(collegeShow);//如果cid为0,移除该元素
            }
        }
        
        return c_Info;
    }

}
