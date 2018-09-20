/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Blog;
import Entity.Collect;
import Entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class CollectFacade extends AbstractFacade<Collect> {

    @PersistenceContext(unitName = "blogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Collect getCollectByBlogAndUser(Blog b, User u){
        Query query = em.createNamedQuery("Collect.findByBlogAndUser");
        query.setParameter("Blog", b);
        query.setParameter("User", u);
        return (Collect)query.getSingleResult();
    }
    
    
    
    public CollectFacade() {
        super(Collect.class);
    }
    
}
