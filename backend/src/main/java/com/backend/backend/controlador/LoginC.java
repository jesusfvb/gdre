package com.backend.backend.controlador;


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
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String usuario, @RequestParam String contrasenna)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, contrasenna));
        } catch (Exception e) {
            throw new Exception("Usuario o Contraseña Incorrecta", e);
        }
        final UserDetails userDetails = serviceUserDetails.loadUserByUsername(usuario);
        final String jwt = serviceJwt.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

    public ResponseEntity<Void> prueva(@RequestParam Object cucu,@RequestParam Object apn){
        return  ResponseEntity.ok(null);
    }
}
