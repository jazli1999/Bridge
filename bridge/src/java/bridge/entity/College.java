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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
 * @author Jasmi
 */
@Entity
@Table(name = "college")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "College.findAll", query = "SELECT c FROM College c")
    , @NamedQuery(name = "College.findByCId", query = "SELECT c FROM College c WHERE c.cId = :cId")
    , @NamedQuery(name = "College.findByCNameCh", query = "SELECT c FROM College c WHERE c.cNameCh = :cNameCh")
    , @NamedQuery(name = "College.findByCNameEn", query = "SELECT c FROM College c WHERE c.cNameEn = :cNameEn")
    , @NamedQuery(name = "College.findByCCountry", query = "SELECT c FROM College c WHERE c.cCountry = :cCountry")
    , @NamedQuery(name = "College.findByCSite", query = "SELECT c FROM College c WHERE c.cSite = :cSite")
    , @NamedQuery(name = "College.findByCPic", query = "SELECT c FROM College c WHERE c.cPic = :cPic")
    , @NamedQuery(name = "College.findByCTofel", query = "SELECT c FROM College c WHERE c.cTofel = :cTofel")
    , @NamedQuery(name = "College.findByCGre", query = "SELECT c FROM College c WHERE c.cGre = :cGre")
    , @NamedQuery(name = "College.findByCGPA", query = "SELECT c FROM College c WHERE c.cGPA = :cGPA")
    , @NamedQuery(name = "College.findByCFee", query = "SELECT c FROM College c WHERE c.cFee = :cFee")})
public class College implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_id")
    private Integer cId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "c_name_ch")
    private String cNameCh;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "c_name_en")
    private String cNameEn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "c_country")
    private String cCountry;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "c_site")
    private String cSite;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "c_info")
    private String cInfo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "c_pic")
    private String cPic;
    @Column(name = "c_tofel")
    private Long cTofel;
    @Column(name = "c_gre")
    private Long cGre;
    @Column(name = "c_GPA")
    private Long cGPA;
    @Column(name = "c_fee")
    private Long cFee;
    @JoinTable(name = "user_stars_college", joinColumns = {
        @JoinColumn(name = "college_c_id", referencedColumnName = "c_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_u_id", referencedColumnName = "u_id")})
    @ManyToMany
    private Collection<User> userCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collegeCId")
    private Collection<User> userCollection1;

    public College() {
    }

    public College(Integer cId) {
        this.cId = cId;
    }

    public College(Integer cId, String cNameCh, String cNameEn, String cCountry, String cSite, String cInfo, String cPic) {
        this.cId = cId;
        this.cNameCh = cNameCh;
        this.cNameEn = cNameEn;
        this.cCountry = cCountry;
        this.cSite = cSite;
        this.cInfo = cInfo;
        this.cPic = cPic;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public String getCNameCh() {
        return cNameCh;
    }

    public void setCNameCh(String cNameCh) {
        this.cNameCh = cNameCh;
    }

    public String getCNameEn() {
        return cNameEn;
    }

    public void setCNameEn(String cNameEn) {
        this.cNameEn = cNameEn;
    }

    public String getCCountry() {
        return cCountry;
    }

    public void setCCountry(String cCountry) {
        this.cCountry = cCountry;
    }

    public String getCSite() {
        return cSite;
    }

    public void setCSite(String cSite) {
        this.cSite = cSite;
    }

    public String getCInfo() {
        return cInfo;
    }

    public void setCInfo(String cInfo) {
        this.cInfo = cInfo;
    }

    public String getCPic() {
        return cPic;
    }

    public void setCPic(String cPic) {
        this.cPic = cPic;
    }

    public Long getCTofel() {
        return cTofel;
    }

    public void setCTofel(Long cTofel) {
        this.cTofel = cTofel;
    }

    public Long getCGre() {
        return cGre;
    }

    public void setCGre(Long cGre) {
        this.cGre = cGre;
    }

    public Long getCGPA() {
        return cGPA;
    }

    public void setCGPA(Long cGPA) {
        this.cGPA = cGPA;
    }

    public Long getCFee() {
        return cFee;
    }

    public void setCFee(Long cFee) {
        this.cFee = cFee;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection1() {
        return userCollection1;
    }

    public void setUserCollection1(Collection<User> userCollection1) {
        this.userCollection1 = userCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cId != null ? cId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof College)) {
            return false;
        }
        College other = (College) object;
        if ((this.cId == null && other.cId != null) || (this.cId != null && !this.cId.equals(other.cId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bridge.entity.College[ cId=" + cId + " ]";
    }
    
}
