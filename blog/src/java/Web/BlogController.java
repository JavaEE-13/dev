package Web;

import Entity.Blog;
import Entity.Category;
import Entity.Comment;

import Web.util.JsfUtil;
import Web.util.PaginationHelper;
import Session.BlogFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

@Named("blogController")
@SessionScoped
public class BlogController implements Serializable {

    private Blog current;
    private DataModel items = null;
    @EJB
    private Session.BlogFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    private Collection<Blog> hots;
    //private Collection<Blog> blogsByCate;
    private Collection<Blog> searchedBlogs;
    
    private String diyLabel;

     public String getDiyLabel() {
        return diyLabel;
    }
   
    public void setDiyLabel(String diyLabel) {
        this.diyLabel = diyLabel;
    }
    
    public String searchByDLabel() {
        this.searchedBlogs = new ArrayList<>();
        try {
            this.searchedBlogs = ejbFacade.getBlogByDIYLabel(this.diyLabel);
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return "searchBlog.xhtml";
    }
  
    public Collection<Blog> getSearchedBlogs(){
        return this.searchedBlogs;
    }
           
    public void setSearchedBlogs(Collection<Blog> result){
        this.searchedBlogs = result;
    }
    
    public String getBlogDetail(Blog blog){
        current = blog;
        return "thePost.xhtml";
    }
    
    
    
    public String MyprepareView() {
        current = (Blog)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ThePost";
    }
    
    public String MyprepareCreate() {
        current = new Blog();
        
        //不知道这行怎么改
        //current.setPostPK(new entity.Blog());
        selectedItemIndex = -1;
        return "Post";
    }
    
    public String Mycreate() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
            return MyprepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String Mynext() {
        getPagination().nextPage();
        recreateModel();
        return "Posts";
    }
    
    public String Myprevious() {
        getPagination().previousPage();
        recreateModel();
        return "Posts";
    }
    
     public Comment[] MygetReplyItems(Collection<Comment> replyCollection) {
        
        int size = replyCollection.size();
       
        Comment[] replies = replyCollection.toArray(new Comment[size]);
       
        return replies;
    
    }
    
     
     //找什么？
    public SelectItem[] MygetItemsAvailableSelectOne(entity.BlogNo id) {
        return JsfUtil.MygetSelectItems(ejbFacade.find(id), true);
    }
    
    public BlogController() {
    }

    public Collection<Blog> getHotBlogs() {
        try{
        hots = ejbFacade.getHotBlogs();
        }catch(Exception ex){
            
        }
        return hots;
    }

    public Collection<Blog> getBlogByDIYLabel(String dLabel) {
        Collection<Blog> dBlog = ejbFacade.getBlogByDIYLabel(dLabel);
        return dBlog;
    }

    public Collection<Blog> getBlogsByCategoryId(Category cate) {
        Collection<Blog> blogsByCate = ejbFacade.getBlogsByCate(cate);
        System.out.print("blog controller:");
        System.out.println(blogsByCate.size());

        return blogsByCate;
    }

    public Blog getSelected() {
        if (current == null) {
            current = new Blog();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BlogFacade getFacade() {
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
        current = (Blog) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Blog();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BlogCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Blog) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BlogUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Blog) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BlogDeleted"));
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

    public Blog getBlog(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Blog.class)
    public static class BlogControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BlogController controller = (BlogController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "blogController");
            return controller.getBlog(getKey(value));
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
            if (object instanceof Blog) {
                Blog o = (Blog) object;
                return getStringKey(o.getBlogNo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Blog.class.getName());
            }
        }

    }

}
