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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @author user
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserNo", query = "SELECT u FROM User u WHERE u.userNo = :userNo")
    , @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender")
    , @NamedQuery(name = "User.findByProfile", query = "SELECT u FROM User u WHERE u.profile = :profile")
    , @NamedQuery(name = "User.findByPersonalSign", query = "SELECT u FROM User u WHERE u.personalSign = :personalSign")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserNo")
    private Integer userNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "UserName")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Gender")
    private String gender;
    @Size(max = 100)
    @Column(name = "Profile")
    private String profile;
    @Size(max = 45)
    @Column(name = "PersonalSign")
    private String personalSign;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<Blog> blogCollection;
    @JoinTable(name = "follow", joinColumns = {
        @JoinColumn(name = "User_UserNo", referencedColumnName = "UserNo")}, inverseJoinColumns = {
        @JoinColumn(name = "Follower", referencedColumnName = "UserNo")})
    @ManyToMany
    private Collection<User> userCollection;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<User> userCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userUserNo")
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Collection<Blog> blogCollection1;

    public User() {
    }

    public User(Integer userNo) {
        this.userNo = userNo;
    }

    public User(Integer userNo, String userName, String password, String gender) {
        this.userNo = userNo;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
    }

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPersonalSign() {
        return personalSign;
    }

    public void setPersonalSign(String personalSign) {
        this.personalSign = personalSign;
    }

    @XmlTransient
    public Collection<Blog> getBlogCollection() {
        return blogCollection;
    }

    public void setBlogCollection(Collection<Blog> blogCollection) {
        this.blogCollection = blogCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection1() {
        return userCollection1;
    }

    public void setUserCollection1(Collection<User> userCollection1) {
        this.userCollection1 = userCollection1;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<Blog> getBlogCollection1() {
        return blogCollection1;
    }

    public void setBlogCollection1(Collection<Blog> blogCollection1) {
        this.blogCollection1 = blogCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userNo != null ? userNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userNo == null && other.userNo != null) || (this.userNo != null && !this.userNo.equals(other.userNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.User[ userNo=" + userNo + " ]";
    }
    
}
