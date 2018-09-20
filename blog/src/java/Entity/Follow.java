/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "follow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Follow.findAll", query = "SELECT f FROM Follow f")
    , @NamedQuery(name = "Follow.findByUserUserNo", query = "SELECT f FROM Follow f WHERE f.followPK.userUserNo = :userUserNo")
    , @NamedQuery(name = "Follow.findByFollower", query = "SELECT f FROM Follow f WHERE f.followPK.follower = :follower")
    , @NamedQuery(name = "Follow.FindByUserAndFollower", query = "SELECT f FROM Follow f WHERE f.followPK.follower = :follower AND f.followPK.userUserNo = :userUserNo ")})
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FollowPK followPK;
    @JoinColumn(name = "Message_MessageNo", referencedColumnName = "NotifyNo")
    @ManyToOne(optional = false)
    private Notify messageMessageNo;
    @JoinColumn(name = "User_UserNo", referencedColumnName = "UserNo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "Follower", referencedColumnName = "UserNo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;

    public Follow() {
    }

    public Follow(FollowPK followPK) {
        this.followPK = followPK;
    }

    public Follow(int userUserNo, int follower) {
        this.followPK = new FollowPK(userUserNo, follower);
    }

    public FollowPK getFollowPK() {
        return followPK;
    }

    public void setFollowPK(FollowPK followPK) {
        this.followPK = followPK;
    }

    public Notify getMessageMessageNo() {
        return messageMessageNo;
    }

    public void setMessageMessageNo(Notify messageMessageNo) {
        this.messageMessageNo = messageMessageNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (followPK != null ? followPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Follow)) {
            return false;
        }
        Follow other = (Follow) object;
        if ((this.followPK == null && other.followPK != null) || (this.followPK != null && !this.followPK.equals(other.followPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Follow[ followPK=" + followPK + " ]";
    }

}
