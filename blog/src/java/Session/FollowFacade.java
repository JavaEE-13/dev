/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Follow;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class FollowFacade extends AbstractFacade<Follow> {

    @PersistenceContext(unitName = "blogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Follow getFollowByUserAndFollower(int userNo, int followerNo) {
        Query query = em.createNamedQuery("Follow.FindByUserAndFollower");
        query.setParameter("userUserNo", userNo);
        query.setParameter("follower", followerNo);
        try {
            Follow f = (Follow) query.getSingleResult();
            return f;
        } catch (NoResultException ex) {
            //error message?
            return null;
        }

    }

    public FollowFacade() {
        super(Follow.class);
    }

}
