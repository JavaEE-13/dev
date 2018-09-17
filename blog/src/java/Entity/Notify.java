/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "notify")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notify.findAll", query = "SELECT n FROM Notify n")
    , @NamedQuery(name = "Notify.findByNotifyNo", query = "SELECT n FROM Notify n WHERE n.notifyNo = :notifyNo")
    , @NamedQuery(name = "Notify.findByClass1", query = "SELECT n FROM Notify n WHERE n.class1 = :class1")
    , @NamedQuery(name = "Notify.findByFlag", query = "SELECT n FROM Notify n WHERE n.flag = :flag")})
public class Notify implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NotifyNo")
    private Integer notifyNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Class")
    private int class1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Flag")
    private int flag;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageMessageNo")
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageMessageNo")
    private Collection<Follow> followCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageMessageNo")
    private Collection<Collect> collectCollection;
    @JoinColumn(name = "User_UserNo", referencedColumnName = "UserNo")
    @ManyToOne(optional = false)
    private User userUserNo;

    public Notify() {
    }

    public Notify(Integer notifyNo) {
        this.notifyNo = notifyNo;
    }

    public Notify(Integer notifyNo, int class1, int flag) {
        this.notifyNo = notifyNo;
        this.class1 = class1;
        this.flag = flag;
    }

    public Integer getNotifyNo() {
        return notifyNo;
    }

    public void setNotifyNo(Integer notifyNo) {
        this.notifyNo = notifyNo;
    }

    public int getClass1() {
        return class1;
    }

    public void setClass1(int class1) {
        this.class1 = class1;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<Follow> getFollowCollection() {
        return followCollection;
    }

    public void setFollowCollection(Collection<Follow> followCollection) {
        this.followCollection = followCollection;
    }

    @XmlTransient
    public Collection<Collect> getCollectCollection() {
        return collectCollection;
    }

    public void setCollectCollection(Collection<Collect> collectCollection) {
        this.collectCollection = collectCollection;
    }

    public User getUserUserNo() {
        return userUserNo;
    }

    public void setUserUserNo(User userUserNo) {
        this.userUserNo = userUserNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notifyNo != null ? notifyNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notify)) {
            return false;
        }
        Notify other = (Notify) object;
        if ((this.notifyNo == null && other.notifyNo != null) || (this.notifyNo != null && !this.notifyNo.equals(other.notifyNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Notify[ notifyNo=" + notifyNo + " ]";
    }
    
}
