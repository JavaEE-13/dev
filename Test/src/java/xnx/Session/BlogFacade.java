/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xnx.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;
import xnx.Entity.Blog;

/**
 *
 * @author user
 */
@Stateless
public class BlogFacade extends AbstractFacade<Blog> {

    @PersistenceContext(unitName = "TestPU")
    private EntityManager em;
    
            
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List getBlogByCateAndLabel(String cate, String label){
        Query query = em.createNamedQuery("Blog.findByCateAndLabel");
        query.setParameter("categoryCategoryId", cate);
        query.setParameter("label", label);
        List blogListCateByLabel = (List) query.getResultList();
        return blogListCateByLabel;
    }
    
    public BlogFacade() {
        super(Blog.class);
    }
    
}
