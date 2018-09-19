/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author 96007
 */
@Embeddable
public class PostPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PostId")
    private int postId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PostName")
    private String postName;

    public PostPK() {
    }

    public PostPK(int postId, String postName) {
        this.postId = postId;
        this.postName = postName;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) postId;
        hash += (postName != null ? postName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PostPK)) {
            return false;
        }
        PostPK other = (PostPK) object;
        if (this.postId != other.postId) {
            return false;
        }
        if ((this.postName == null && other.postName != null) || (this.postName != null && !this.postName.equals(other.postName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PostPK[ postId=" + postId + ", postName=" + postName + " ]";
    }
    
}
