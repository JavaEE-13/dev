/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "blogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }

    public List findBlogByCateAndLabel(String cate, String label) {
        Query query = em.createNamedQuery("Category.findByCateAndLabel");
        query.setParameter("label", label);
        query.setParameter("cate", cate);
        List blogCollection = null;
        try {
            Category category = (Category) query.getSingleResult();
            blogCollection = (List) category.getBlogCollection();
        } catch (Exception e) {
            return blogCollection;
        }

        return blogCollection;
    }

}
