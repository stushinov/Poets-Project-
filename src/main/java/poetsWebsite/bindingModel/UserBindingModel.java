package poetsWebsite.bindingModel;

import com.sun.istack.internal.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Created by Admin on 10.12.2016 г..
 */
public class UserBindingModel {

    @NotNull
    private String email;

    @NotNull
    private String fullName;

    @NotNull
    private String city;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
