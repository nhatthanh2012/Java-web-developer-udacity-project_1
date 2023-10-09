package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;

    // config by ThanhTLN
    public WebSecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    // Override by ThanhTLN
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    // Override by ThanhTLN
    protected void configure(HttpSecurity http) throws Exception {
        // config by ThanhTLN
        // http.authorizeRequests().anyRequest().permitAll(); // allow user to access this address
        http.csrf().disable(); // Tắt CSRF để H2 Console hoạt động
        http.headers().frameOptions().disable(); // Tắt frameOptions để H2 Console hoạt động

        http.authorizeRequests()
                .antMatchers("/h2-console/*", "/signup", "/submit-signup", "/login", "/css/**", "/js/**").permitAll() // allow user to access this address
                .anyRequest().authenticated(); // all other request must authenticate before access

        // require user login
        http.formLogin()
                .loginPage("/login") // allow user authenticate by form login
                .permitAll() // allow user to access this address after login
                .defaultSuccessUrl("/home", true);

        // handle logout
        http.logout() // allow logout
            .logoutSuccessUrl("/login")
            .permitAll();
    }
}
