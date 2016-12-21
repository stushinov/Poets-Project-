package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import poetsWebsite.bindingModel.UserBindingModel;
import poetsWebsite.entity.Role;
import poetsWebsite.entity.User;
import poetsWebsite.repository.CategoryRepository;
import poetsWebsite.repository.RoleRepository;
import poetsWebsite.repository.UserRepository;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Admin on 10.12.2016 г..
 */
@Controller
public class UserControler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    //Register get
    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("view", "user/register");

        return "layout";
    }

    @GetMapping("/error/registerError")
    public String registerError(Model model){

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("view", "error/registerError");
        return "layout";
    }

    @PostMapping("/error/registerError")
    public String registerErrorProcess(UserBindingModel userBindingModel){

        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            return "redirect:/register";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                userBindingModel.getCity(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword())
        );

        Role userRole = this.roleRepository.findByName("ROLE_USER");

        user.addRole(userRole);

        try {
            this.userRepository.saveAndFlush(user);
        }catch (Exception exception){
            return "redirect:/error/registerError";
        }
        return "redirect:/login";
    }


    //Register Post
    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel){

        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            return "redirect:/register";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                userBindingModel.getCity(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword())
        );

        Role userRole = this.roleRepository.findByName("ROLE_USER");

        user.addRole(userRole);

        //Just in case we have the email already
        try {
            this.userRepository.saveAndFlush(user);
        }catch (Exception exception){
            return "redirect:/error/registerError";
        }
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login(Model model){

        model.addAttribute("view", "user/login");
        return "layout";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){

        //It checks if there is logged in user and if there is,
        // it simply tells the authentication module to logout the user. Then it redirects to the login page again.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }


    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String userDetails(Model model){

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                                                                    .getAuthentication()
                                                                    .getPrincipal();

        User user = userRepository.findByEmail(principal.getUsername());

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("view", "user_details/details");

        return "layout";
    }

}
