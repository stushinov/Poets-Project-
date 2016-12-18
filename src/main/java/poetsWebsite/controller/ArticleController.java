package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import poetsWebsite.bindingModel.ArticleBindingModel;
import poetsWebsite.entity.Article;
import poetsWebsite.entity.User;
import poetsWebsite.repository.ArticleRepository;
import poetsWebsite.repository.UserRepository;

import java.util.Collections;
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

        Collections.reverse(articles);

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


    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id){
        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }


        //This will get our authentication token and check if the type of user is anonymous.
        //If the authentication is anonymous it means that we don't have logged in user.
        //In the body of our if, we need to get our current use
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Article article = this.articleRepository.findOne(id);

        model.addAttribute("article", article);
        model.addAttribute("view", "articles/details");

        return "layout";
    }
}
