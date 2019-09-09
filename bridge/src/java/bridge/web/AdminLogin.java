/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import bridge.ejb.AdminLoginBean;
@Named
@SessionScoped
public class AdminLogin  implements Serializable {
   
    @EJB
    private AdminLoginBean adminLoginBean;
    
    @NotNull
    protected String ad_name;
    protected String ad_password;
    protected boolean result=true;
    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_password() {
        return ad_password;
    }

    public void setAd_password(String ad_password) {
        this.ad_password = ad_password;
    }
    
   
    public String checkpassword() {
       String temp= adminLoginBean.getResult(this.ad_name, this.ad_password);
       if(temp.equals("false")){this.result=false;}
       else{this.result=true;}
       return temp;
    }
    
   
   

    public boolean isResult() {
        return result;
    }
    
    
}

