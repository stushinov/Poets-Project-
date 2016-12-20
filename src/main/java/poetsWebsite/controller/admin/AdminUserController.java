package poetsWebsite.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poetsWebsite.bindingModel.UserEditBindingModel;
import poetsWebsite.entity.Role;
import poetsWebsite.entity.User;
import poetsWebsite.repository.ArticleRepository;
import poetsWebsite.repository.RoleRepository;
import poetsWebsite.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 20.12.2016 г..
 */
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/")
    public String listUsers(Model model){

        //Get all users in a list
        List<User> users = this.userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "layout";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

        //Some baseCase
        if(!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }

        //We are getting all of the roles from the database and sending them to the view
        //Then we are sending the user we are going to edit to the view as well
        User user = this.userRepository.findOne(id);
        List<Role> roles = this.roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, UserEditBindingModel userEditBindingModel){

        //baseCase
        if(!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);

        //We don't want to change the user password if the admin left the password fields empty
        if(!StringUtils.isEmpty(userEditBindingModel.getPassword()) && !StringUtils.isEmpty(userEditBindingModel.getConfirmPassword())){
            if(userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())){
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bCryptPasswordEncoder.encode(userEditBindingModel.getPassword()));
            }
        }
        // end


        //Editing the full name and/or Email
        user.setFullName(userEditBindingModel.getFullName());
        user.setEmail(userEditBindingModel.getEmail());
        user.setCity(userEditBindingModel.getCity());

        Set<Role> roles = new HashSet<>();

        for(Integer roleId : userEditBindingModel.getRoles()){
            roles.add(this.roleRepository.findOne(id));
        }

        user.setRoles(roles);

        this.userRepository.saveAndFlush(user);

        return "redirect:/admin/users/";

    }

}
