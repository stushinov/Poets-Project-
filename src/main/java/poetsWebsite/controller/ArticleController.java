package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import poetsWebsite.bindingModel.ArticleBindingModel;
import poetsWebsite.entity.Article;
import poetsWebsite.entity.User;
import poetsWebsite.repository.ArticleRepository;
import poetsWebsite.repository.UserRepository;

import java.util.List;

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

        List<Article> articles = this.articleRepository.findAll();
        model.addAttribute("view", "articles/Articles");
        model.addAttribute("articles", articles);
        return "layout";
    }




    @GetMapping("/articles/create")
    public String create(Model model){

        model.addAttribute("view", "articles/create");
        return "layout";
    }




    @PostMapping("/articles/create")
    public String createProcess(ArticleBindingModel articleBindingModel){

        //I added this base case because sometimes when smb leaves an empty text in the forms it takes them as "" and not NULL.
        if(articleBindingModel.getContent().equals("") || articleBindingModel.getTitle().equals("")){
            return "redirect:/articles/create";
        }

        //get the currently logged in user:
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        //String author = userEntity.getFullName();

        Article articleEntity = new Article(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity
        );

        this.articleRepository.saveAndFlush(articleEntity);

        return "redirect:/articles";
    }
}
