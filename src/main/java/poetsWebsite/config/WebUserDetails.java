package poetsWebsite.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import poetsWebsite.entity.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Admin on 8.12.2016 г..
 */
public class WebUserDetails extends User implements UserDetails {

    @Override
    public boolean isAccountNonExpired(){return true;}

    @Override
    public boolean isAccountNonLocked(){return true;}

    @Override
    public boolean isCredentialsNonExpired(){return true;}

    @Override
    public boolean isEnabled(){return true;}



    private ArrayList<String> roles;
    private User user;


    public WebUserDetails(User user, ArrayList<String> roles){
        super(user.getEmail(), user.getFullName(), user.getCity(), user.getPassword());

        this.roles = roles;
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        String userRoles = StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    public User getUser(){
        return this.user;
    }
}
