/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 96007
 */
@Entity
@Table(name = "reply")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reply.findAll", query = "SELECT r FROM Reply r")
    , @NamedQuery(name = "Reply.findByReplyId", query = "SELECT r FROM Reply r WHERE r.replyId = :replyId")})
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ReplyId")
    private Integer replyId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "ReplyContent")
    private String replyContent;
    @JoinColumns({
        @JoinColumn(name = "Post_PostId", referencedColumnName = "PostId")
        , @JoinColumn(name = "Post_PostName", referencedColumnName = "PostName")})
    @ManyToOne(optional = false)
    private Post post;

    public Reply() {
    }

    public Reply(Integer replyId) {
        this.replyId = replyId;
    }

    public Reply(Integer replyId, String replyContent) {
        this.replyId = replyId;
        this.replyContent = replyContent;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (replyId != null ? replyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reply)) {
            return false;
        }
        Reply other = (Reply) object;
        if ((this.replyId == null && other.replyId != null) || (this.replyId != null && !this.replyId.equals(other.replyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Reply[ replyId=" + replyId + " ]";
    }
    
}
