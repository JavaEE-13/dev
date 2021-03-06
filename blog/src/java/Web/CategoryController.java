package Web;

import Entity.Category;
import Entity.Blog;
import Web.util.JsfUtil;
import Web.util.PaginationHelper;
import Session.CategoryFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

@Named("categoryController")
@SessionScoped
public class CategoryController implements Serializable {

    private static final Logger LOG = Logger.getLogger(CategoryController.class.getName());

    private Category current;
    private DataModel items = null;
    @EJB
    private Session.CategoryFacade ejbFacade;

    @EJB
    private Session.BlogFacade blogFacade;

    private PaginationHelper pagination;
    private int selectedItemIndex;

    private List<Category> cateList;

    private Collection<Blog> blogC;
    
    public Collection<Blog> getBlogC(){
        return this.blogC;
    }
    
    public void setBlogC(Collection<Blog> b){
        this.blogC = b;
    }
    
//    private BlogController bControl;
    public CategoryController() {
    }

    public String getBlogByCateAndLabel(String cate, String label) {
        current = ejbFacade.findBlogByCateAndLabel(cate, label);
 
        blogC = new ArrayList<>();
        try {
            blogC.addAll(current.getBlogCollection());
        } catch (NoResultException e) {
            e.printStackTrace();
        } catch(NullPointerException e)  {
            //提示错误消息？?
//            FacesMessage msg = new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("noResult"));
//            throw new NullPointerException(msg);
            e.printStackTrace();
        }    
        return "posts.xhtml";
    }

    public Collection<Blog> getBlogByCategory(String cate) {
        cateList = ejbFacade.findBlogByCategory(cate);

        int size = cateList.size();
//        bControl = new BlogController();
        Collection<Blog> blogC = new ArrayList<>();
        for (Category c : cateList) {
            try {
                blogC.addAll(c.getBlogCollection());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
//            System.out.println("BlogSize");
//            System.out.println(blogC.size());
            Iterator iterator = blogC.iterator();
            while (iterator.hasNext()) {

                Blog blog = (Blog) iterator.next();
//                System.out.println(blog.getTitle());
            }
        } catch (Exception e) {

        }
        return blogC;
    }

    public String getBlogByCategoryId(int cateId) {
        current = ejbFacade.findBlogByCategoryId(cateId);
        return "#";
    }

    public Category getSelected() {
        if (current == null) {
            current = new Category();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CategoryFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Category) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Category();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoryCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Category) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoryUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Category) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoryDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Category getCategory(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Category.class)
    public static class CategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CategoryController controller = (CategoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "categoryController");
            return controller.getCategory(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Category) {
                Category o = (Category) object;
                return getStringKey(o.getCategoryId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Category.class.getName());
            }
        }

    }

}
