package com.backend.backend.repositorio.entidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Cuarto extends Entidad {

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "apartamento_id")
    private Apartamento apartamento;

    @OneToMany(mappedBy = "cuarto", fetch = FetchType.EAGER)
    private Set<Usuario> usuarios = new HashSet<>();

    public Cuarto(Integer id) {
        super.setId(id);
    }
}
