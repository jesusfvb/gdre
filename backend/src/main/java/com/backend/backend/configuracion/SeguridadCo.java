package com.backend.backend.configuracion;

import com.backend.backend.filtro.JwtRequestF;
import com.backend.backend.servicios.implementacion.UserDetailsSI;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class SeguridadCo extends WebSecurityConfigurerAdapter {

    private final UserDetailsSI serviceUserDetails;

    private final JwtRequestF filterJwtRequest;

    public final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(serviceUserDetails).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/swagger-ui/index.html").permitAll()
                .antMatchers("/swagger-ui/swagger-ui.css").permitAll()
                .antMatchers("/swagger-ui/swagger-ui-bundle.js").permitAll()
                .antMatchers("/swagger-ui/swagger-ui-standalone-preset.js").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/v3/api-docs/swagger-config").permitAll()
                .antMatchers(HttpMethod.OPTIONS).anonymous().anyRequest().authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filterJwtRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
