/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Blog;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class BlogFacade extends AbstractFacade<Blog> {

    @PersistenceContext(unitName = "BlogWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BlogFacade() {
        super(Blog.class);
    }
    
}
