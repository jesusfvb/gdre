package com.backend.backend.servicios.implementacion;

import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.UsuarioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsSI implements UserDetailsService {

    @Autowired
    private UsuarioS usuarioS;

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        Usuario usuario = usuarioS.getByUsuario(arg0);
        Set<GrantedAuthority> authorities = new HashSet<>();
        usuario.getRoles().forEach(rol -> {
            authorities.add(new SimpleGrantedAuthority(rol.name()));
        });
        return new User(usuario.getUsuario(), usuario.getContrasena(), authorities);
    }

}
