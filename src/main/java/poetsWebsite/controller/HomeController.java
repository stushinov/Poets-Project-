package poetsWebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Admin on 10.12.2016 г..
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("view", "home/index");

        return "layout";
    }

    @GetMapping("/about")
    public String getAbout(Model model){

        model.addAttribute("view", "home/about");
        return "layout";
    }

    @RequestMapping("/error/403")
    public String accessDenied(Model model){

        model.addAttribute("view", "error/403");
        return "layout";
    }
}
