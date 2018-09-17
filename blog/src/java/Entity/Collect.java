/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "collect")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collect.findAll", query = "SELECT c FROM Collect c")
    , @NamedQuery(name = "Collect.findByCollectNo", query = "SELECT c FROM Collect c WHERE c.collectNo = :collectNo")})
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CollectNo")
    private Integer collectNo;
    @JoinColumn(name = "Blog_BlogNo", referencedColumnName = "BlogNo")
    @ManyToOne(optional = false)
    private Blog blogBlogNo;
    @JoinColumn(name = "User_UserNo", referencedColumnName = "UserNo")
    @ManyToOne(optional = false)
    private User userUserNo;
    @JoinColumn(name = "Message_MessageNo", referencedColumnName = "NotifyNo")
    @ManyToOne(optional = false)
    private Notify messageMessageNo;

    public Collect() {
    }

    public Collect(Integer collectNo) {
        this.collectNo = collectNo;
    }

    public Integer getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(Integer collectNo) {
        this.collectNo = collectNo;
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

    public Notify getMessageMessageNo() {
        return messageMessageNo;
    }

    public void setMessageMessageNo(Notify messageMessageNo) {
        this.messageMessageNo = messageMessageNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectNo != null ? collectNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collect)) {
            return false;
        }
        Collect other = (Collect) object;
        if ((this.collectNo == null && other.collectNo != null) || (this.collectNo != null && !this.collectNo.equals(other.collectNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Collect[ collectNo=" + collectNo + " ]";
    }
    
}
