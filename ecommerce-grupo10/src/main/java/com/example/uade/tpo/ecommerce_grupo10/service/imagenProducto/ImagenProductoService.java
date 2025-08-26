package com.example.uade.tpo.ecommerce_grupo10.service.imagenProducto;

import java.util.List;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ImagenProductoDTO;

public interface ImagenProductoService {

    // crear con una url
    ImagenProductoDTO agregarImagen(Long productoId, String url);

    // actualizar solo la URL
    ImagenProductoDTO actualizarUrl(Long productoId, Long imagenId, String nuevaUrl);

    void eliminarImagen(Long productoId, Long imagenId);

    List<ImagenProductoDTO> listarPorProducto(Long productoId);

    ImagenProductoDTO obtener(Long productoId, Long imagenId);
}
