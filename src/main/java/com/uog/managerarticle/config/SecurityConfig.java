package com.uog.managerarticle.config;

import com.uog.managerarticle.service.impl.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private  CustomAuthenticationSuccessHandle customAuthenticationSuccessHandle;

    private CustomerUserDetailService customerUserDetailService;

    @Autowired
    public SecurityConfig(CustomerUserDetailService customerUserDetailService, CustomAuthenticationSuccessHandle customAuthenticationSuccessHandle) {
        this.customerUserDetailService = customerUserDetailService;
        this.customAuthenticationSuccessHandle = customAuthenticationSuccessHandle;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(customerUserDetailService);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/file-article/**","/image/**").permitAll()
                .antMatchers("/login","/article/download").permitAll()
                .antMatchers("/").authenticated()
                .antMatchers("/faculty/statistics-report","faculty/statistics-report", "/article/statistics-report/**","/article/exception-report/**").access("hasAuthority('MANAGER')")
                .antMatchers( "/article/post/**","/topic/post","/student/article/list").access("hasAuthority('STUDENT')")
                .antMatchers("/article/manager/**","/student/article/{id}").access("hasAuthority('COORDINATOR')")
                .antMatchers("/article/detail/**").access("hasAnyAuthority('STUDENT','COORDINATOR')")
                .antMatchers("/topic/manager").access("hasAnyAuthority('MANAGER','ADMIN')")
                .antMatchers("/student/saveedit").access("hasAnyAuthority('STUDENT')")
                .antMatchers("/manager/saveedit").access("hasAnyAuthority('MANAGER')")
                .antMatchers("/coordinator/saveedit").access("hasAnyAuthority('COORDINATOR')")
                .antMatchers("/topic/**","/manager/**","/coordinator/**","/student/**").access("hasAuthority('ADMIN')")
                .antMatchers("/article/guest").access("hasAuthority('GUEST')")
                .antMatchers("/topic/post").access("hasAnyAuthority('COORDINATOR','STUDENT')")
                .antMatchers("/article/list").access("hasAnyAuthority('MANAGER','GUEST')")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/handlerLogin")
                .successHandler(customAuthenticationSuccessHandle)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

}
