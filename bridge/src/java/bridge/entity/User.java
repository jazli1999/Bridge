/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUId", query = "SELECT u FROM User u WHERE u.uId = :uId")
    , @NamedQuery(name = "User.findByUUsername", query = "SELECT u FROM User u WHERE u.uUsername = :uUsername")
    , @NamedQuery(name = "User.findByUName", query = "SELECT u FROM User u WHERE u.uName = :uName")
    , @NamedQuery(name = "User.findByUPassword", query = "SELECT u FROM User u WHERE u.uPassword = :uPassword")
    , @NamedQuery(name = "User.findByUGPA", query = "SELECT u FROM User u WHERE u.uGPA = :uGPA")
    , @NamedQuery(name = "User.findByUTofel", query = "SELECT u FROM User u WHERE u.uTofel = :uTofel")
    , @NamedQuery(name = "User.findByUGre", query = "SELECT u FROM User u WHERE u.uGre = :uGre")
    , @NamedQuery(name = "User.findByUWechat", query = "SELECT u FROM User u WHERE u.uWechat = :uWechat")
    , @NamedQuery(name = "User.findByUEmail", query = "SELECT u FROM User u WHERE u.uEmail = :uEmail")
    , @NamedQuery(name = "User.findByUMajor", query = "SELECT u FROM User u WHERE u.uMajor = :uMajor")
    , @NamedQuery(name = "User.findByUState", query = "SELECT u FROM User u WHERE u.uState = :uState")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "u_id")
    private Integer uId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "u_username")
    private String uUsername;
    @Size(max = 255)
    @Column(name = "u_name")
    private String uName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "u_password")
    private String uPassword;
    @Column(name = "u_GPA")
    private Long uGPA;
    @Column(name = "u_tofel")
    private Long uTofel;
    @Column(name = "u_gre")
    private Long uGre;
    @Size(max = 45)
    @Column(name = "u_wechat")
    private String uWechat;
    @Size(max = 45)
    @Column(name = "u_email")
    private String uEmail;
    @Lob
    @Size(max = 65535)
    @Column(name = "u_info")
    private String uInfo;
    @Size(max = 45)
    @Column(name = "u_major")
    private String uMajor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "u_state")
    private int uState;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "u_avatar")
    private byte[] uAvatar;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<College> collegeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userUId")
    private Collection<Sharing> sharingCollection;
    @JoinColumn(name = "college_c_id", referencedColumnName = "c_id")
    @ManyToOne(optional = false)
    private College collegeCId;

    public User() {
    }

    public User(Integer uId) {
        this.uId = uId;
    }

    public User(Integer uId, String uUsername, String uPassword, int uState, byte[] uAvatar) {
        this.uId = uId;
        this.uUsername = uUsername;
        this.uPassword = uPassword;
        this.uState = uState;
        this.uAvatar = uAvatar;
    }

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public String getUUsername() {
        return uUsername;
    }

    public void setUUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Long getUGPA() {
        return uGPA;
    }

    public void setUGPA(Long uGPA) {
        this.uGPA = uGPA;
    }

    public Long getUTofel() {
        return uTofel;
    }

    public void setUTofel(Long uTofel) {
        this.uTofel = uTofel;
    }

    public Long getUGre() {
        return uGre;
    }

    public void setUGre(Long uGre) {
        this.uGre = uGre;
    }

    public String getUWechat() {
        return uWechat;
    }

    public void setUWechat(String uWechat) {
        this.uWechat = uWechat;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getUInfo() {
        return uInfo;
    }

    public void setUInfo(String uInfo) {
        this.uInfo = uInfo;
    }

    public String getUMajor() {
        return uMajor;
    }

    public void setUMajor(String uMajor) {
        this.uMajor = uMajor;
    }

    public int getUState() {
        return uState;
    }

    public void setUState(int uState) {
        this.uState = uState;
    }

    public byte[] getUAvatar() {
        return uAvatar;
    }

    public void setUAvatar(byte[] uAvatar) {
        this.uAvatar = uAvatar;
    }

    @XmlTransient
    public Collection<College> getCollegeCollection() {
        return collegeCollection;
    }

    public void setCollegeCollection(Collection<College> collegeCollection) {
        this.collegeCollection = collegeCollection;
    }

    @XmlTransient
    public Collection<Sharing> getSharingCollection() {
        return sharingCollection;
    }

    public void setSharingCollection(Collection<Sharing> sharingCollection) {
        this.sharingCollection = sharingCollection;
    }

    public College getCollegeCId() {
        return collegeCId;
    }

    public void setCollegeCId(College collegeCId) {
        this.collegeCId = collegeCId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uId != null ? uId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.uId == null && other.uId != null) || (this.uId != null && !this.uId.equals(other.uId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bridge.entity.User[ uId=" + uId + " ]";
    }
    
}
