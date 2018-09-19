/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
 * @author 96007
 */
@Entity
@Table(name = "post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p")
    , @NamedQuery(name = "Post.findByPostId", query = "SELECT p FROM Post p WHERE p.postPK.postId = :postId")
    , @NamedQuery(name = "Post.findByPostName", query = "SELECT p FROM Post p WHERE p.postPK.postName = :postName")})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PostPK postPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "PostContent")
    private String postContent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private Collection<Reply> replyCollection;
 

    public Post() {
    }

    public Post(PostPK postPK) {
        this.postPK = postPK;
    }

    public Post(PostPK postPK, String postContent) {
        this.postPK = postPK;
        this.postContent = postContent;
    }

    public Post(int postId, String postName) {
        this.postPK = new PostPK(postId, postName);
    }

    public PostPK getPostPK() {
        return postPK;
    }

    public void setPostPK(PostPK postPK) {
        this.postPK = postPK;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    @XmlTransient
    public Collection<Reply> getReplyCollection() {
        return replyCollection;
    }

    public void setReplyCollection(Collection<Reply> replyCollection) {
        this.replyCollection = replyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postPK != null ? postPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.postPK == null && other.postPK != null) || (this.postPK != null && !this.postPK.equals(other.postPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  postPK.getPostId()+"#"+postPK.getPostName();
    }
    
}
