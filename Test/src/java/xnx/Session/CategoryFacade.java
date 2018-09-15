/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xnx.Session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import xnx.Entity.Category;

/**
 *
 * @author user
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "TestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List findBlogByCateAndLabel(String cate, String label){
        Query query = em.createNamedQuery("Category.findByCateAndLabel");
        query.setParameter("label",label);
        query.setParameter("category", cate);
        Category category= (Category)query.getSingleResult();
        List blogCollection = (List)category.getBlogCollection();
        return blogCollection;
    }
    
    public CategoryFacade() {
        super(Category.class);
    }
    
}
