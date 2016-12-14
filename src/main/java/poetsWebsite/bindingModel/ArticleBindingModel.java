package poetsWebsite.bindingModel;

import poetsWebsite.entity.User;

import javax.validation.constraints.NotNull;

/**
 * Created by Admin on 14.12.2016 г..
 */
public class ArticleBindingModel {

    @NotNull
    private String title;

    @NotNull
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
