package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import poetsWebsite.repository.ArticleRepository;
import poetsWebsite.repository.UserRepository;

/**
 * Created by Admin on 14.12.2016 г..
 */
@Controller
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/articles")
    public String renderView(Model model){

        model.addAttribute("view", "articles/Articles");
        return "layout";
    }
}
