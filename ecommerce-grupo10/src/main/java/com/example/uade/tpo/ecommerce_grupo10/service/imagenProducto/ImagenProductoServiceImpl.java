package com.example.uade.tpo.ecommerce_grupo10.service.imagenProducto;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.uade.tpo.ecommerce_grupo10.entity.ImagenProducto;
import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ImagenProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__.MapperImagenProducto;
import com.example.uade.tpo.ecommerce_grupo10.exceptions.RecursoNoEncontrado;
import com.example.uade.tpo.ecommerce_grupo10.repository.ImagenProductoRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.ProductoRepository;


import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class ImagenProductoServiceImpl implements ImagenProductoService {
    
    private final ImagenProductoRepository imagenRepository;
    private final ProductoRepository productoRepository;
    private final MapperImagenProducto mapperImagenProducto;

    @Override @Transactional
    public ImagenProductoDTO agregarImagen(Long productoId, String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria");
        }

        // validar producto
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontrado("Producto no encontrado"));

        // evitar duplicados por URL
        if (imagenRepository.existsByProductoIdAndUrl(productoId, url)) {
            throw new IllegalArgumentException("Ya existe una imagen con esa URL para este producto");
        }

        // crear y guardar
        ImagenProducto img = new ImagenProducto();
        img.setUrl(url);
        img.setProducto(producto);

        ImagenProducto guardada = imagenRepository.save(img);
        return mapperImagenProducto.toDTO(guardada);
    }

    @Override // listar las imagenes por cada producto por su id
    @Transactional(readOnly = true)
    public List<ImagenProductoDTO> listarPorProducto(Long productoId) {
        return imagenRepository.findByProductoId(productoId)
                .stream()
                .map(mapperImagenProducto::toDTO)
                .toList();
    }

    @Override // obtener una imagen por su id y el id del producto
    @Transactional(readOnly = true)
    public ImagenProductoDTO obtener(Long productoId, Long imagenId) {
        ImagenProducto img = imagenRepository.findByIdAndProductoId(imagenId, productoId)
                .orElseThrow(() -> new RecursoNoEncontrado("Imagen no encontrada para este producto"));
        return mapperImagenProducto.toDTO(img);
    }

    @Override @Transactional
    public ImagenProductoDTO actualizarUrl(Long productoId, Long imagenId, String nuevaUrl) {
        if (nuevaUrl == null || nuevaUrl.isBlank()) {
            throw new IllegalArgumentException("La nueva URL no puede estar vacía");
        }

        ImagenProducto img = imagenRepository.findByIdAndProductoId(imagenId, productoId)
                .orElseThrow(() -> new RecursoNoEncontrado("Imagen no encontrada para este producto"));

        boolean repetida = imagenRepository.existsByProductoIdAndUrl(productoId, nuevaUrl)
                && !nuevaUrl.equals(img.getUrl());
        if (repetida) {
            throw new IllegalArgumentException("Otra imagen ya usa esa URL para este producto");
        }

        img.setUrl(nuevaUrl); 
        return mapperImagenProducto.toDTO(img);
    }

    @Override @Transactional
    public void eliminarImagen(Long productoId, Long imagenId) {
        ImagenProducto img = imagenRepository.findByIdAndProductoId(imagenId, productoId)
                .orElseThrow(() -> new RecursoNoEncontrado("Imagen no encontrada para este producto"));
        imagenRepository.delete(img);
    }

}
