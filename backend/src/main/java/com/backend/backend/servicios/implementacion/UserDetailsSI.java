package com.backend.backend.servicios.implementacion;

import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.UsuarioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsSI implements UserDetailsService {

    private final UsuarioS usuarioS;

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        Usuario usuario = usuarioS.getByUsuario(arg0);
        return new User(usuario.getUsuario(), usuario.getContrasena(), usuario.getRoles()
                .parallelStream().map(rol -> new SimpleGrantedAuthority(rol.name()))
                .collect(Collectors.toList()));
    }

}
