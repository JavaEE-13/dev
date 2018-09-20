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
import javax.persistence.NoResultException;
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

    public Category findBlogByCateAndLabel(String cate, String label) {
        Query query = em.createNamedQuery("Category.findByCateAndLabel");
        query.setParameter("label", label);
        query.setParameter("cate", cate);
        try {
            Category category = (Category) query.getSingleResult();
            return category;
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List findBlogByCategory(String cate) {
        Query query = em.createNamedQuery("Category.findByCategory");
        query.setParameter("category", cate);
        try {
            List<Category> category = query.getResultList();

            return category;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Category findBlogByCategoryId(int categoryId) {
        Query query = em.createNamedQuery("Category.findByCategoryId");

        query.setParameter("cateory", categoryId);

        try {
            Category category = (Category) query.getSingleResult();
            return category;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
