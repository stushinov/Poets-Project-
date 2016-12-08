package poetsWebsite.entity;

import javax.persistence.*;

/**
 * Created by Admin on 8.12.2016 г..
 */
@Entity
@Table(name = "users")
public class User {

    private Integer id;

    private String email;

    private String fullName;

    private String city;

    private String password;



    //Constructor
    public User(String email, String fullName, String city, String password){
        this.email = email;
        this.fullName = fullName;
        this.city = city;
        this.password = password;
    }

    public User(){  }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }



    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    @Column(name = "city")
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }



    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
