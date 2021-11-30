package com.backend.backend.servicios.implementacion;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsSI implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMINISTRADOR";
            }
        });
        return new User("admin", "1234", authorities);
    }

}
