/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xnx.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import xnx.Entity.User;

/**
 *
 * @author user
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "TestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public User loginCheck(String userName, String password) {
        Query query = em.createNamedQuery("findByUserNameAndPassword");
        query.setParameter("userName", userName);
        query.setParameter("password", password);

        User user = (User) query.getSingleResult();

        return user;
    }

}
