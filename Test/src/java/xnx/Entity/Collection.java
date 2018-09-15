/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xnx.Entity;

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
@Table(catalog = "blogdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collection.findAll", query = "SELECT c FROM Collection c")
    , @NamedQuery(name = "Collection.findByCollectionNo", query = "SELECT c FROM Collection c WHERE c.collectionNo = :collectionNo")})
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer collectionNo;
    @JoinColumn(name = "Blog_BlogNo", referencedColumnName = "BlogNo", nullable = false)
    @ManyToOne(optional = false)
    private Blog blogBlogNo;
    @JoinColumn(name = "User_UserNo", referencedColumnName = "UserNo", nullable = false)
    @ManyToOne(optional = false)
    private User userUserNo;

    public Collection() {
    }

    public Collection(Integer collectionNo) {
        this.collectionNo = collectionNo;
    }

    public Integer getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(Integer collectionNo) {
        this.collectionNo = collectionNo;
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
        hash += (collectionNo != null ? collectionNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collection)) {
            return false;
        }
        Collection other = (Collection) object;
        if ((this.collectionNo == null && other.collectionNo != null) || (this.collectionNo != null && !this.collectionNo.equals(other.collectionNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Collection[ collectionNo=" + collectionNo + " ]";
    }
    
}
