/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xnx.Entity;

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
@Table(catalog = "blogdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserNo", query = "SELECT u FROM User u WHERE u.userNo = :userNo")
    , @NamedQuery(name = "User.findByUserNameAndPassword", query = "SELECT u FROM User u WHERE u.userName = :userName AND u.password = :userName")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender")
    , @NamedQuery(name = "User.findByProfile", query = "SELECT u FROM User u WHERE u.profile = :profile")
    , @NamedQuery(name = "User.findByPersonalSign", query = "SELECT u FROM User u WHERE u.personalSign = :personalSign")
    , @NamedQuery(name = "User.findByFollowerNum", query = "SELECT u FROM User u WHERE u.followerNum = :followerNum")
    , @NamedQuery(name = "User.findByLevel", query = "SELECT u FROM User u WHERE u.level = :level")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer userNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String gender;
    @Size(max = 100)
    @Column(length = 100)
    private String profile;
    @Size(max = 45)
    @Column(length = 45)
    private String personalSign;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Follower_Num", nullable = false)
    private int followerNum;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int level;
    @JoinTable(name = "follow", joinColumns = {
        @JoinColumn(name = "User_UserNo", referencedColumnName = "UserNo", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "Follower", referencedColumnName = "UserNo", nullable = false)})
    @ManyToMany
    private Collection<User> userCollection;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<User> userCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userUserNo")
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userUserNo")
    private Collection<xnx.Entity.Collection> collectionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Collection<Blog> blogCollection;

    public User() {
    }

    public User(Integer userNo) {
        this.userNo = userNo;
    }

    public User(Integer userNo, String userName, String password, String gender, int followerNum, int level) {
        this.userNo = userNo;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.followerNum = followerNum;
        this.level = level;
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

    public int getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
    public Collection<xnx.Entity.Collection> getCollectionCollection() {
        return collectionCollection;
    }

    public void setCollectionCollection(Collection<xnx.Entity.Collection> collectionCollection) {
        this.collectionCollection = collectionCollection;
    }

    @XmlTransient
    public Collection<Blog> getBlogCollection() {
        return blogCollection;
    }

    public void setBlogCollection(Collection<Blog> blogCollection) {
        this.blogCollection = blogCollection;
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
