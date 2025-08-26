package com.example.uade.tpo.ecommerce_grupo10.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.ecommerce_grupo10.entity.ImagenProducto;

@Repository
public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Long>{
    
    // traer todas las imgs de un producto
    List<ImagenProducto> findByProductoId(Long productoId);

    // buscar una imagen en particular de un producto
    Optional<ImagenProducto> findByIdAndProductoId(Long id, Long productoId);

    // verificar si ya existe una URL asociada a un producto
    boolean existsByProductoIdAndUrl(Long productoId, String url);

    // contar imgs de un producto, para validar
    int countByProductoId(Long productoId);
}
