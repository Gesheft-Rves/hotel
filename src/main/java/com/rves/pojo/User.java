package com.rves.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "accountNonExpired")
    private boolean accountNonExpired;
    @Column(name = "accountNonLocked")
    private boolean accountNonLocked;
    @Column(name = "credentialsNonExpired")
    private boolean credentialsNonExpired;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @Version
    private Integer version;

    @Column(name = "authorities")
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    private List<Role> authorities;

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public User() {
    }

}
