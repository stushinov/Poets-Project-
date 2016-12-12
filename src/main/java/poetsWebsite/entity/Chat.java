package poetsWebsite.entity;

import javax.persistence.*;

/**
 * Created by Admin on 12.12.2016 г..
 */
@Entity
@Table(name = "chat")
public class Chat {

    private Integer id;

    private String author;

    private String content;

    public Chat(String author, String content){
        this.author = author;
        this.content = content;
    }

    public Chat(){  }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
