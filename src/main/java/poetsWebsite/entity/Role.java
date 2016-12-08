package poetsWebsite.entity;

import javax.persistence.*;

/**
 * Created by Admin on 8.12.2016 г..
 */
@Entity
@Table(name = "roles")
public class Role {

    private Integer id;

    private String name;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
