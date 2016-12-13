package poetsWebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import poetsWebsite.bindingModel.ChatBindingModel;
import poetsWebsite.entity.Chat;
import poetsWebsite.entity.User;
import poetsWebsite.repository.ChatRepository;
import poetsWebsite.repository.UserRepository;

/**
 * Created by Admin on 12.12.2016 г..
 */
@Controller
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/chat")
    public String chat(Model model){

        model.addAttribute("view", "liveChat/chat");

        return "layout";
    }

    @PostMapping("/chat")
    public String chatProcess(ChatBindingModel chatBindingModel){

        String author = "";

        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            User getUserDetails = userRepository.findByEmail(user.getUsername());
            author = getUserDetails.getFullName();

        } catch (Exception e){
            author = "Anonymous";
        }
            chatBindingModel.setAuthor(author);
        Chat chatEntity = new Chat(
                chatBindingModel.getAuthor(),
                chatBindingModel.getContent()
        );

        this.chatRepository.saveAndFlush(chatEntity);

        return "redirect:/chat";
    }
}
