package poetsWebsite.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poetsWebsite.entity.User;
import poetsWebsite.repository.ArticleRepository;
import poetsWebsite.repository.RoleRepository;
import poetsWebsite.repository.UserRepository;

import java.util.List;

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

}
