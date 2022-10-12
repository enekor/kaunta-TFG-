/*package com.kunta.kaunta_api.config.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/clase/").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/clase/{id}").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/clase/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/clase/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/course/").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/course/{id}").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/course/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/course/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/modulo/").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/modulo/{id}").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/modulo/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/modulo/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/pupil/").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/pupil/{id}").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/pupil/name/{name}").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/pupil/email/{email}").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/pupil/me/").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/pupil/login").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/pupil/").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/pupil/create").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/pupil/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/pupil/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/tuition/").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/tuition/{id}").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/tuition/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/tuition/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/tuition/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/files/{filename:.+}").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/files/").permitAll()

                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}*/
