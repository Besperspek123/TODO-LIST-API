package spring.rest.shop.springrestshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    public User(Long id, String username, Boolean activity, String password,String passwordConfirm, String email) {
        this.id = id;
        this.username = username;
        this.activity = activity;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }
    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "activity")
    private Boolean activity;

    @Column(name = "password")
    private String password;


    @Transient
    private String passwordConfirm;

    @JsonIgnore
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "balance")
    private long Balance;


    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", activity=" + activity +
                ", email='" + email + '\'' +
                ", Balance=" + Balance +
                '}';
    }
}
