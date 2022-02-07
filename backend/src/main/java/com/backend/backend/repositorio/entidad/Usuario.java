package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance
public class Usuario extends Entidad {


    public enum Rol {Usuario, Estudiante, Profesor, Administrador}

    @Column
    private String nombre;

    @Column
    private String usuario;

    @Column
    private String contrasena;

    @Column()
    private Boolean ubicar;

    @ManyToOne()
    @JoinColumn(name = "cuarto_id")
    private Cuarto cuarto;

    @OneToMany(mappedBy = "coordinador", orphanRemoval = true)
    private List<Guardia> guardias = new ArrayList<>();

    @OneToMany(mappedBy = "participante", orphanRemoval = true)
    private List<Integrante> participacion = new ArrayList<>();

    @ElementCollection
    @Column(name = "role")
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "owner_id"))
    @Enumerated(EnumType.STRING)
    private Set<Rol> roles = new LinkedHashSet<>();

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        super.setId(idUsuario);
    }

    public Usuario(UsuarioNewSo usuario) {
        this.nombre = usuario.getNombre();
        this.roles.add(Rol.Usuario);
    }

    public List<Integrante> getParticipacion() {
        return participacion;
    }

    public void setParticipacion(List<Integrante> participacion) {
        this.participacion = participacion;
    }

    public List<Guardia> getGuardias() {
        return guardias;
    }

    public void setGuardias(List<Guardia> guardias) {
        this.guardias = guardias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public Boolean getUbicar() {
        return ubicar;
    }

    public void setUbicar(Boolean ubicar) {
        this.ubicar = ubicar;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Usuario confirmar() {
        this.ubicar = true;
        return this;
    }

    public Usuario desconfirmar() {
        this.ubicar = null;
        return this;
    }

    public Usuario cuartoNull() {
        this.cuarto = null;
        return this;
    }

    public Usuario addCuarto(Cuarto cuarto) {
        setCuarto(cuarto);
        return this;
    }

    @Override
    public UsuarioResp convertir() {
        return new UsuarioResp(super.getId(), this.nombre);
    }

    public UsuarioUbicacionResp convertir2() {
        Integer edificio = (this.cuarto != null) ? this.cuarto.getApartamento().getEdificio().getNumero() : null;
        Integer apartamento = (this.cuarto != null) ? this.cuarto.getApartamento().getNumero() : null;
        Integer cuarto = (this.cuarto != null) ? this.cuarto.getNumero() : null;
        return new UsuarioUbicacionResp(super.getId(), this.nombre, edificio, apartamento, cuarto);
    }
}
