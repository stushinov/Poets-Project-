package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import poetsWebsite.entity.User;
import poetsWebsite.repository.UserRepository;

/**
 * Created by Admin on 12.12.2016 г..
 */
@Controller
public class ChatController {

    @GetMapping("/chat")
    public String chat(Model model){

        model.addAttribute("view", "liveChat/chat");

        return "layout";
    }
}
