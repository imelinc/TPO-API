package com.example.uade.tpo.ecommerce_grupo10.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
    // catalogo disponible, stock > 0
    Page<Producto> findByStockGreaterThan(int stock, Pageable pageable);

    // Busquedas
    Page<Producto> findByTituloContainingIgnoreCaseAndStockGreaterThan(String titulo, int stock, Pageable pageable);
    Page<Producto> findByCategoriaIdAndStockGreaterThan(Long categoriaId, int stock, Pageable pageable); 

}
