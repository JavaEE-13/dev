/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
    , @NamedQuery(name = "Comment.findByContent", query = "SELECT c FROM Comment c WHERE c.content = :content")
    , @NamedQuery(name = "Comment.findByCreatedTime", query = "SELECT c FROM Comment c WHERE c.createdTime = :createdTime")
    , @NamedQuery(name = "Comment.findByCommentNo", query = "SELECT c FROM Comment c WHERE c.commentNo = :commentNo")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 140)
    @Column(name = "Content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Created_Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CommentNo")
    private Integer commentNo;
    @JoinColumn(name = "Blog_BlogNo", referencedColumnName = "BlogNo")
    @ManyToOne(optional = false)
    private Blog blogBlogNo;
    @JoinColumn(name = "User_UserNo", referencedColumnName = "UserNo")
    @ManyToOne(optional = false)
    private User userUserNo;

    public Comment() {
    }

    public Comment(Integer commentNo) {
        this.commentNo = commentNo;
    }

    public Comment(Integer commentNo, String content, Date createdTime) {
        this.commentNo = commentNo;
        this.content = content;
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(Integer commentNo) {
        this.commentNo = commentNo;
    }

    public Blog getBlogBlogNo() {
        return blogBlogNo;
    }

    public void setBlogBlogNo(Blog blogBlogNo) {
        this.blogBlogNo = blogBlogNo;
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
        hash += (commentNo != null ? commentNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentNo == null && other.commentNo != null) || (this.commentNo != null && !this.commentNo.equals(other.commentNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Comment[ commentNo=" + commentNo + " ]";
    }
    
}
