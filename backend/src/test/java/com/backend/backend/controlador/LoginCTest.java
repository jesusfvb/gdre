package com.backend.backend.controlador;

import com.backend.backend.controlador.solicitudes.LoginSo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LoginCTest {

    @Autowired
    LoginC loginC;

    @Test
    void Test() throws Exception {
        String jwt = (String) loginC.createAuthenticationToken(new LoginSo("admin", "1234")).getBody();
        assertNotNull(jwt);
    }

}