package Web;

import Entity.Blog;
import Entity.Collect;
import Entity.Follow;
import Entity.FollowPK;
import Entity.User;
import Web.util.JsfUtil;
import Web.util.PaginationHelper;
import Session.UserFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@Named("userController")
@SessionScoped
public class UserController implements Serializable, Validator {

    private User current;
    private DataModel items = null;
    @EJB
    private Session.UserFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    @EJB
    private Session.BlogFacade blogFacade;
    @EJB
    private Session.FollowFacade followFacade;

    @EJB
    private Session.UserFacade userFacade;
    
    private String userName;
    private String password;

    private String passwordConfirm;
<<<<<<< HEAD
=======

    
    
    
    
>>>>>>> cb9e1f787e33438fdf4c0290f9ac2c772ee1f5dc
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getpasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordComfirm) {
        this.passwordConfirm = passwordComfirm;
    }

    public String processSignIn() {
        try {
            current = ejbFacade.matchUser(this.userName,this.password);
            System.out.println(current.getUserName());
            return "/homePageLogIn.xhtml";
        } catch (Exception e) {
            current = null;
        }
        return null;
    }

    public String prepareSignOut(){
        current = null;
        return "/homaPage.xhtml";
    }
    
    public Collection<Blog> getMyBlogs(){
        Collection<Blog> myBlogs= new ArrayList<>();
        myBlogs = current.getBlogCollection();
        return myBlogs;
    }
    
    public Collection<Blog> getMyCollecion(){
        Collection<Collect> myCollection = new ArrayList<>();
        Collection<Blog> myCollectBlog = new ArrayList<>();
        myCollection = current.getCollectCollection();
        int size = myCollection.size();
        for(Collect c: myCollection){
            myCollectBlog.add(blogFacade.getBlogByBlogNo(c.getBlogBlogNo()));
        }
        return myCollectBlog;
    }
    
     
    
    
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        UIComponent c = null;

        for (UIComponent ui : component.getParent().getChildren()) {
            if ("password".equals(ui.getId())) {
                c = ui;
                break;
            }
            
            HtmlInputText htmlInputText = (HtmlInputText) c;
            if (!value.toString().trim().equals(htmlInputText.toString().trim())) {
                FacesMessage msg = new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("PasswordDoNotMatch"));
                throw new ValidatorException(msg);
            }
        }
    }

    public UserController() {
    }

    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserFacade getFacade() {
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
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareSignUp() {
        current = new User();
        selectedItemIndex = -1;
        return "signin.xhtml";
    }
    
    public String signup() {
        //String pass_ = this.passwordConfirm;
        try {
            if (passwordConfirm == null ? current.getPassword() != null : !passwordConfirm.equals(current.getPassword())) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PasswordDoNotMatch"));
                return null;
            }
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareSignUp();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
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

    public User getUser(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
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
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getUserNo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + User.class.getName());
            }
        }

    }
}

