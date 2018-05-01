package com.rves.configuration;


import com.rves.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/resources/**", "/login/**").permitAll()
            .antMatchers("/booking/**").hasAnyRole("ADMIN","USER")

            .antMatchers("/rooms/details/").hasAnyRole("ADMIN","USER","CLEANER")
            .antMatchers("/rooms/form/").hasAnyRole("ADMIN","USER")
            .antMatchers("/rooms/list/").hasAnyRole("ADMIN","USER")

            .antMatchers("/type/**").hasAnyRole("ADMIN","USER")
            .antMatchers("/users/**").hasRole("ADMIN")
            .antMatchers("/cleanRoom/**").hasAnyRole("ADMIN","CLEANER")
            .anyRequest().authenticated()
        .and()
        .formLogin()
            .loginPage("/login")
            .successForwardUrl("/home")
            .permitAll()
            .and()
        .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .permitAll()
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());

    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
