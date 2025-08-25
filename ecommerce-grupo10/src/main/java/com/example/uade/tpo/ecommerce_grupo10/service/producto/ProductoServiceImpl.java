package com.example.uade.tpo.ecommerce_grupo10.service.producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.uade.tpo.ecommerce_grupo10.entity.Categoria;
import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__.MapperProducto;
import com.example.uade.tpo.ecommerce_grupo10.exceptions.RecursoNoEncontrado;
import com.example.uade.tpo.ecommerce_grupo10.repository.CategoriaRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MapperProducto mapperProducto;

    private Categoria getCategoria(Long categoriaId){
        return categoriaRepository.findById(categoriaId).orElseThrow(() -> new RecursoNoEncontrado("Categoria no encontrada"));
    }

    @Override
    public Producto save(ProductoDTO dto) {
        var categoria = getCategoria(dto.getCategoriaId());
        var entity = mapperProducto.toEntity(dto, categoria);
        return productoRepository.save(entity);
    }

    @Override
    public Producto get(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontrado("Producto no encontrado"));
    }

    @Override
    public Producto update(Long id, ProductoDTO dto) {
        var entity = get(id);
        Categoria cat = (dto.getCategoriaId() != null) ? getCategoria(dto.getCategoriaId()) : entity.getCategoria();
        mapperProducto.updateEntityFromDto(dto, entity, cat);
        return productoRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        var entity = get(id); // lanza excepcion si no existe
        productoRepository.delete(entity);
    }


    // BUSQUEDAS

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

    @Override
    public Page<Producto> buscarPorPrecio(Double precioMin, Double precioMax, Pageable pageable) {
        return productoRepository.findByPrecioBetweenAndStockGreaterThan(precioMin, precioMax, 0, pageable);
    }

}
