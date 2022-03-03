package com.backend.backend.repositorio.entidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Usuario extends Entidad {

    public enum Rol {Usuario, Estudiante, Profesor, Instructora, Vicedecano, Administrador}

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String contrasena;

    @Column(unique = true, nullable = false)
    private String solapin;

    @Column()
    private Boolean ubicar;

    @ManyToOne()
    @JoinColumn(name = "cuarto_id")
    private Cuarto cuarto;

    @OneToMany(mappedBy = "coordinador", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Guardia> guardias = new ArrayList<>();

    @OneToMany(mappedBy = "participante", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Integrante> participacion = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role", nullable = false)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "owner_id"))
    @Enumerated(EnumType.STRING)
    private List<Rol> roles = new LinkedList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "usuario_id")
    private List<Cuarteleria> cuartelerias = new ArrayList<>();

    public Usuario(Integer idUsuario) {
        super.setId(idUsuario);
        this.nombre = "";
        this.usuario = "";
        this.contrasena = "";
        this.solapin = "";
    }

}
