package poetsWebsite.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poetsWebsite.bindingModel.CategoryBindingModel;
import poetsWebsite.entity.Category;
import poetsWebsite.repository.ArticleRepository;
import poetsWebsite.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 21.12.2016 г..
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;


    // /LIST
    @GetMapping("/")
    public String list(Model model){

        List<Category> categories = this.categoryRepository.findAll();

        categories = categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());

        model.addAttribute("view", "admin/category/list");
        model.addAttribute("categories", categories);
        return "layout";
    }


    //CREATE
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view", "admin/category/create");
        return "layout";
    }
    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel){

        //First, we need to check if the field is empty and if it is, we should redirect the user
        if(StringUtils.isEmpty(categoryBindingModel.getName())){
            return "redirect:/admin/categories/create";
        }

        Category category = new Category(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);

        return "redirect:/admin/categories/";
    }


    //EDIT
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        //Gets current category
        Category category = this.categoryRepository.findOne(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");

        return "layout";
    }
    @PostMapping("/edit/{id}")
    public String createProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findOne(id);

        category.setName(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);

        return "redirect:/admin/categories/";
    }


}
