package poetsWebsite.bindingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 20.12.2016 г..
 */
public class UserEditBindingModel extends UserBindingModel {

    private List<Integer> roles;

    public UserEditBindingModel() {
        this.roles = new ArrayList<>();
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
