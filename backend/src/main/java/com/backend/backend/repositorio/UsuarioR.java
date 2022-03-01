package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioR extends JpaRepository<Usuario, Integer> {

    List<Usuario> findAllByCuarto_Id(Integer id);

    List<Usuario> findAllByCuartoIsNullAndUbicarIsTrue();

    List<Usuario> findByCuartoIsNullAndUbicarIsNullAndRoles(Usuario.Rol roles);

    List<Usuario> findAllByCuartoIsNotNull();

    Usuario findByUsuario(String usuario);

    List<Usuario> findByRoles(Usuario.Rol roles);

}
