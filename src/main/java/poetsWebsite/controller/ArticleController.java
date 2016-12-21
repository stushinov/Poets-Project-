package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import poetsWebsite.entity.Category;
import poetsWebsite.entity.User;
import poetsWebsite.repository.ArticleRepository;
import poetsWebsite.repository.CategoryRepository;
import poetsWebsite.repository.UserRepository;

import javax.persistence.Transient;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 14.12.2016 г..
 */
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/articles")
    public String renderView(Model model){

        List<Article> articles = this.articleRepository.findAll();

        Collections.reverse(articles);

        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("view", "articles/Articles");
        model.addAttribute("articles", articles);
        return "layout";
    }




    @GetMapping("/articles/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model){

        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "articles/create");
        return "layout";
    }




    @PostMapping("/articles/create")
    @PreAuthorize("isAuthenticated()")
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

        Category category = this.categoryRepository.findOne(articleBindingModel.getCategoryId());

        Article articleEntity = new Article(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity,
                category
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

    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete( Model model, @PathVariable  Integer id){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "articles/delete");

        return "layout";
    }

    @PostMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        this.articleRepository.delete(article);

        return "redirect:/articles";
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        model.addAttribute("view", "articles/edit");
        model.addAttribute("article", article);

        return "layout";
    }


    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, ArticleBindingModel articleBindingModel){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        //Get the current article
        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        //Set the current article content to the input form in 'edit'
        article.setContent(articleBindingModel.getContent());

        //Set the current article title to the input form in 'edit'
        article.setTitle(articleBindingModel.getTitle());

        //Save the edited article to the database
        this.articleRepository.saveAndFlush(article);

        //redirects the user to the article details
        return "redirect:/article/" + article.getId();
    }


    private boolean isUserAuthorOrAdmin(Article article){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }
}
