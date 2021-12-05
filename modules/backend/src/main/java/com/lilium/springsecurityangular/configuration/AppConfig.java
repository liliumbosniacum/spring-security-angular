package com.lilium.springsecurityangular.configuration;

import com.lilium.springsecurityangular.session.SessionFilter;
import com.lilium.springsecurityangular.user.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private SessionFilter sessionFilter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(currentUserService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http.exceptionHandling().authenticationEntryPoint(
                (request, response, ex) -> response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage())
        ).and();

        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(
                sessionFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
