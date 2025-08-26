package com.example.uade.tpo.ecommerce_grupo10.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.ecommerce_grupo10.entity.Rol;
import com.example.uade.tpo.ecommerce_grupo10.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    // algunas busquedas basicas
    // usamos todas las posibilidades, por eso hacemos un ignorecase
    Optional<Usuario> findByUsernameIgnoreCase(String username);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    
    // ver si existen segun parametros
    // aca si respetamos case ya que puede ser mas critico
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // filtrar por roles
    @Query("""
            SELECT u
            FROM Usuario u
            JOIN u.roles r
            WHERE r = :rol
            """)
    List<Usuario> findAllByRol(@Param("rol") Rol rol);

    // filtrar roles pero con paginacion
    @Query("""
            SELECT u
            FROM Usuario u
            JOIN u.roles r
            WHERE r = :rol
            """)
    Page<Usuario> findAllByRol(@Param("rol") Rol rol, Pageable pageable);

    // buscar por username, email o nombre completo
    @Query("""
            SELECT u
            FROM Usuario u
            WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :parametroBusqueda, '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', :parametroBusqueda, '%'))
            OR LOWER(u.nombreCompleto) LIKE LOWER(CONCAT('%', :parametroBusqueda, '%'))
            """)
    Page<Usuario> search(@Param("parametroBusqueda") String parametroBusqueda, Pageable pageable);

}
