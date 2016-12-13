package poetsWebsite.bindingModel;

import javax.validation.constraints.NotNull;

/**
 * Created by Admin on 12.12.2016 г..
 */
public class ChatBindingModel {

    @NotNull
    private String author;

    @NotNull
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
