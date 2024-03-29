package com.backend.backend.filtro;

import com.backend.backend.servicios.implementacion.JwtSI;
import com.backend.backend.servicios.implementacion.UserDetailsSI;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JwtRequestF extends OncePerRequestFilter {

    private final UserDetailsSI serviceUserDetails;

    private final JwtSI serviceJwt;

    @Override
    protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
            throws ServletException, IOException {

        final String authorizationHeader = arg0.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        if (authorizationHeader != null) {
            jwt = authorizationHeader;
            userName = serviceJwt.extractUserName(jwt);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.serviceUserDetails.loadUserByUsername(userName);
            if (serviceJwt.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(arg0));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        arg2.doFilter(arg0, arg1);
    }

}
