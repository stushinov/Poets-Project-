package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import poetsWebsite.repository.RoleRepository;
import poetsWebsite.repository.UserRepository;

/**
 * Created by Admin on 10.12.2016 г..
 */
@Controller
public class UserControler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    //Register get
    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("view", "user/register");

        return "layout";
    }


}
