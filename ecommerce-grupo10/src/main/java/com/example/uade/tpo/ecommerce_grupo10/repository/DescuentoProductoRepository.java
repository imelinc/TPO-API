package com.example.uade.tpo.ecommerce_grupo10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.ecommerce_grupo10.entity.DescuentoProducto;

@Repository
public interface DescuentoProductoRepository extends JpaRepository<DescuentoProducto, Long>{
    
    Optional<DescuentoProducto> findByProductoId(Long productoId);
    boolean existsByProductoId(Long productoId);
    
}
