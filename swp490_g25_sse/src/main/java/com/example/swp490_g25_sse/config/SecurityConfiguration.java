/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.config;

import com.example.swp490_g25_sse.filter.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.swp490_g25_sse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    private UserService userService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public FilterRegistrationBean<AuthTokenFilter> filterRegistrationBean() {
        FilterRegistrationBean< AuthTokenFilter> registrationBean = new FilterRegistrationBean();
        AuthTokenFilter customURLFilter = new AuthTokenFilter();

        registrationBean.setFilter(customURLFilter);
        registrationBean.addUrlPatterns("/api/**");
        registrationBean.setOrder(2); //set precedence
        return registrationBean;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestCache().disable();

        http
                
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/registration**",
                        "/js/**",
                        "/css/**",
                        "/images/**")
                .permitAll()
                .antMatchers("/app/student/**").hasRole("STUDENT")
                .antMatchers("/app/teacher/**").hasRole("TEACHER")
                .antMatchers(HttpMethod.POST, "/api/course").hasRole("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/course/*").hasRole("TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/course/*").hasRole("TEACHER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/app")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();

    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
