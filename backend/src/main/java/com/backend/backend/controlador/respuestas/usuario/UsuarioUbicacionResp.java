package com.backend.backend.controlador.respuestas.usuario;

public class UsuarioUbicacionResp {

    private Integer id;

    private String nombre;

    private Integer edificio;

    private Integer apartamento;

    private Integer cuarto;

    public UsuarioUbicacionResp(Integer id, String nombre, Integer edificio, Integer apartamento, Integer cuarto) {
        this.id = id;
        this.nombre = nombre;
        this.edificio = edificio;
        this.apartamento = apartamento;
        this.cuarto = cuarto;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getEdificio() {
        return edificio;
    }

    public Integer getApartamento() {
        return apartamento;
    }

    public Integer getCuarto() {
        return cuarto;
    }
}
