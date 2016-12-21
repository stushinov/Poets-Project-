package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poetsWebsite.entity.Category;
import poetsWebsite.repository.CategoryRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 10.12.2016 г..
 */
@Controller
public class HomeController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String getHome(Model model){

        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("categories", categories);
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
