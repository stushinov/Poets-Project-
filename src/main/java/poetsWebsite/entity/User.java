package poetsWebsite.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    private Set<Article> articles;

    private Set<Role> roles;


    //Constructor
    public User(String email, String fullName, String city, String password){
        this.email = email;
        this.fullName = fullName;
        this.city = city;
        this.password = password;

        this.articles = new HashSet<>();
        this.roles = new HashSet<>();
    }

    public User(){  }


    public void addRole(Role role){
        this.roles.add(role);
    }


    @OneToMany(mappedBy = "author")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

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



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



    @Transient
    public boolean isPro(){
        if(articlesCount() == 100){
            return true;
        }
        return false;
    }

    @Transient
    public int articlesCount(){
        if(this.getArticles().size() > 50){
            return 100;
        }
        return this.getArticles().size() *2;
    }

    @Transient
    public boolean isAdmin(){

        //If the admin role exists in the current user roles. It returns true or false based on the result.
        return this.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    @Transient
    public boolean isAuthor(Article article){

        //Comparing the author id with our user id and again return true or false based on the result
        return Objects.equals(this.getId(), article.getAuthor().getId());
    }

}
