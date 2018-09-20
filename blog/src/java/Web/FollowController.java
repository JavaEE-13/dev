package Web;

import Entity.Follow;
import Entity.FollowPK;
import Entity.User;
import Web.util.JsfUtil;
import Web.util.PaginationHelper;
import Session.FollowFacade;

import java.io.Serializable;
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

@Named("followController")
@SessionScoped
public class FollowController implements Serializable {

    private Follow current;
    private DataModel items = null;
    @EJB
    private Session.FollowFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    @EJB
    private Session.UserFacade userFacade;      
    
    private User author;
    
    public String getAuthorDetail(User u){
        setAuthor(u);
        return "selfinfo.xhtml";
    }
    
    public User getAuthor(){
        return author;
    }

    public void setAuthor(User u){
        this.author = u;
    }
    
    
    public void createFollow(User u1, User u2){
        if(u1 == u2){
            JsfUtil.addErrorMessage("You can not follow yourself");
        }
        if(userFacade.getFollowByBlogAndUser(u1, u2) != null){
            JsfUtil.addErrorMessage("You have already Followed.");
        }
        Follow f = new Follow(new FollowPK(u1.getUserNo(), u2.getUserNo()));
        ejbFacade.create(f);
   
        int i = u1.getFollowerNum();
        i = i + 1;
        u1.setFollowerNum(i);
        userFacade.edit(u1);
        JsfUtil.addSuccessMessage("Follow success");
    }
    
    
    
    
    public FollowController() {
    }

    public Follow getSelected() {
        if (current == null) {
            current = new Follow();
            current.setFollowPK(new Entity.FollowPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private FollowFacade getFacade() {
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
        current = (Follow) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Follow();
        current.setFollowPK(new Entity.FollowPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getFollowPK().setUserUserNo(current.getUser().getUserNo());
            current.getFollowPK().setFollower(current.getUser1().getUserNo());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FollowCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Follow) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getFollowPK().setUserUserNo(current.getUser().getUserNo());
            current.getFollowPK().setFollower(current.getUser1().getUserNo());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FollowUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Follow) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FollowDeleted"));
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

    public Follow getFollow(Entity.FollowPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Follow.class)
    public static class FollowControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FollowController controller = (FollowController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "followController");
            return controller.getFollow(getKey(value));
        }

        Entity.FollowPK getKey(String value) {
            Entity.FollowPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entity.FollowPK();
            key.setUserUserNo(Integer.parseInt(values[0]));
            key.setFollower(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(Entity.FollowPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUserUserNo());
            sb.append(SEPARATOR);
            sb.append(value.getFollower());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Follow) {
                Follow o = (Follow) object;
                return getStringKey(o.getFollowPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Follow.class.getName());
            }
        }

    }

}
