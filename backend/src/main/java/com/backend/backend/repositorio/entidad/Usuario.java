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

    @Column
    private String nombre;

    @Column
    private String usuario;

    @Column
    private String contrasena;

    @Column
    private String solapin;

    @Column()
    private Boolean ubicar;

    @ManyToOne()
    @JoinColumn(name = "cuarto_id")
    private Cuarto cuarto;

    @OneToMany(mappedBy = "coordinador", orphanRemoval = true)
    private List<Guardia> guardias = new ArrayList<>();

    @OneToMany(mappedBy = "participante", orphanRemoval = true)
    private List<Integrante> participacion = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "owner_id"))
    @Enumerated(EnumType.STRING)
    private List<Rol> roles = new LinkedList<>();

    public Usuario(Integer idUsuario) {
        super.setId(idUsuario);
    }

}
