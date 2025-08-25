package com.example.uade.tpo.ecommerce_grupo10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired // inyeccion de la dependencia del repositorio
    ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        validar(producto);
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        validar(producto);
        productoRepository.save(producto);
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Page<Producto> listarDisponibles(Pageable pageable) {
        return productoRepository.findByStockGreaterThan(0, pageable);
    }

    @Override
    public Page<Producto> buscarPorTitulo(String titulo, Pageable pageable) {
        return productoRepository.findByTituloContainingIgnoreCaseAndStockGreaterThan(titulo, 0, pageable);
    }

    @Override
    public Page<Producto> buscarPorCategoria(Long categoriaId, Pageable pageable) {
        return productoRepository.findByCategoriaIdAndStockGreaterThan(categoriaId, 0, pageable);
    }

    private void validar(Producto p){
        // metodo para validar los datos del prducto
        // antes de guardarlo en la base de datos
        if (p.getPrecio()<0) throw new IllegalArgumentException("El precio no puede ser negativo");
        if (p.getStock()<0) throw new IllegalArgumentException("El stock no puede ser negativo");
        if (p.getTitulo() == null || p.getTitulo().isBlank()) throw new IllegalArgumentException("Titulo requerido."); 
    }

}
