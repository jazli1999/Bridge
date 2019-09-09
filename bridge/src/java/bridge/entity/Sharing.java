/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridge.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jasmi
 */
@Entity
@Table(name = "sharing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sharing.findAll", query = "SELECT s FROM Sharing s")
    , @NamedQuery(name = "Sharing.findBySId", query = "SELECT s FROM Sharing s WHERE s.sId = :sId")
    , @NamedQuery(name = "Sharing.findBySTitle", query = "SELECT s FROM Sharing s WHERE s.sTitle = :sTitle")
    , @NamedQuery(name = "Sharing.findBySDate", query = "SELECT s FROM Sharing s WHERE s.sDate = :sDate")
    , @NamedQuery(name = "Sharing.findBySUp", query = "SELECT s FROM Sharing s WHERE s.sUp = :sUp")
    , @NamedQuery(name = "Sharing.orderByDate", query = "SELECT s FROM Sharing s order by s.sDate desc")
    , @NamedQuery(name = "Sharing.findBySearch", query = "SELECT s FROM Sharing s WHERE s.sTitle LIKE :keyWord ")
    , @NamedQuery(name = "Sharing.orderByUp", query = "SELECT s FROM Sharing s order by s.sUp desc")
    , @NamedQuery(name="Sharing.findbyUid",query="SELECT s FROM Sharing s Where s.userUId=:sUid")
    , @NamedQuery(name = "userupsharing.findSpecific", query = "SELECT c FROM Sharing s JOIN s.userCollection c where s.sId=:sid and c.uId=:uid")
})
public class Sharing implements Serializable {

    @JoinTable(name = "userupsharing", joinColumns = {
        @JoinColumn(name = "sharing_s_id", referencedColumnName = "s_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_u_id", referencedColumnName = "u_id")})
    @ManyToMany
    private Collection<User> userCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "s_id")
    private Integer sId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "s_title")
    private String sTitle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "s_date")
    @Temporal(TemporalType.DATE)
    private Date sDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "s_content")
    private String sContent;
    @Column(name = "s_up")
    private Integer sUp = 0;
    @JoinColumn(name = "user_u_id", referencedColumnName = "u_id")
    @ManyToOne(optional = false)
    private User userUId;

    public Sharing() {
        this.sId = (int) System.currentTimeMillis();
        Date currentTime = new Date();
        this.sDate = currentTime;
    }

    public Sharing(Integer sId) {
        this.sId = (int) System.currentTimeMillis();
        Date currentTime = new Date();
        this.sDate = currentTime;
    }

    public Sharing(Integer sId, String sTitle, Date sDate, String sContent) {
        this.sId = (int) System.currentTimeMillis();
        this.sTitle = sTitle;
        Date currentTime = new Date();
        this.sDate = currentTime;
        this.sContent = sContent;
    }

    public Integer getSId() {
        return sId;
    }

    public void setSId(Integer sId) {
        this.sId = sId;
    }

    public String getSTitle() {
        return sTitle;
    }

    public void setSTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public Date getSDate() {
        return sDate;
    }

    public void setSDate(Date sDate) {
        this.sDate = sDate;
    }

    public String getSContent() {
        return sContent;
    }

    public void setSContent(String sContent) {
        this.sContent = sContent;
    }

    public Integer getSUp() {
        return sUp;
    }

    public void setSUp(Integer sUp) {
        this.sUp = sUp;
    }

    public User getUserUId() {
        return userUId;
    }

    public void setUserUId(User userUId) {
        this.userUId = userUId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sId != null ? sId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sharing)) {
            return false;
        }
        Sharing other = (Sharing) object;
        if ((this.sId == null && other.sId != null) || (this.sId != null && !this.sId.equals(other.sId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bridge.entity.Sharing[ sId=" + sId + " ]";
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

}
