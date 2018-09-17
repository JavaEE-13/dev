/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author user
 */
@Embeddable
public class FollowPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "User_UserNo")
    private int userUserNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Follower")
    private int follower;

    public FollowPK() {
    }

    public FollowPK(int userUserNo, int follower) {
        this.userUserNo = userUserNo;
        this.follower = follower;
    }

    public int getUserUserNo() {
        return userUserNo;
    }

    public void setUserUserNo(int userUserNo) {
        this.userUserNo = userUserNo;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userUserNo;
        hash += (int) follower;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FollowPK)) {
            return false;
        }
        FollowPK other = (FollowPK) object;
        if (this.userUserNo != other.userUserNo) {
            return false;
        }
        if (this.follower != other.follower) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.FollowPK[ userUserNo=" + userUserNo + ", follower=" + follower + " ]";
    }
    
}
