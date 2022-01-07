package com.backend.backend.controlador;


import com.backend.backend.controlador.solicitudes.LoginSo;
import com.backend.backend.controlador.solicitudes.TokenSo;
import com.backend.backend.servicios.implementacion.JwtSI;
import com.backend.backend.servicios.implementacion.UserDetailsSI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginC {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsSI serviceUserDetails;

    @Autowired
    private JwtSI serviceJwt;

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginSo request)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getContrasenna()));
        } catch (Exception e) {
            throw new Exception("Usuario o Contraseña Incorrecta", e);
        }
        final UserDetails userDetails = serviceUserDetails.loadUserByUsername(request.getUsuario());
        final String jwt = serviceJwt.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

    @PutMapping
    private ResponseEntity<Boolean> isTokenExpired(@RequestBody TokenSo token) {
        return ResponseEntity.ok(serviceJwt.isTokenExpired(token.getToken()));
    }
}
