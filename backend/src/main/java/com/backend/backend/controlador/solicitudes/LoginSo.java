package com.backend.backend.controlador.solicitudes;

public class LoginSo {

    private String usuario;

    private String contrasenna;

    public LoginSo() {
    }

    public LoginSo(String usuario, String contrasenna) {
        this.usuario = usuario;
        this.contrasenna = contrasenna;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }
}
