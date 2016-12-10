package poetsWebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
