package com.example.uade.tpo.ecommerce_grupo10.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;

public interface ProductoService {

    public Producto save(Producto producto);
    public Optional<Producto> get(Long id); // se utiliza Optional para manejar el caso en que no se encuentra el producto en la base de datos
    public void update(Producto producto);
    public void delete(Long id);

    Page<Producto> listarDisponibles(Pageable pageable); // productos con stock > 0
    Page<Producto> buscarPorTitulo(String titulo, Pageable pageable); // stock > 0
    Page<Producto> buscarPorCategoria(Long categoriaId, Pageable pageable); // stock > 0
    
}
