package com.example.uade.tpo.ecommerce_grupo10.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uade.tpo.ecommerce_grupo10.entity.Categoria;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.CategoriaDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__.MapperCategoria;
import com.example.uade.tpo.ecommerce_grupo10.service.categoria.CategoriaService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController @RequestMapping("/categorias")
public class CategoriaController {
    
    @Autowired
    CategoriaService categoriaService;

    // listar todas sin paginacion
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodas(){
        List<CategoriaDTO> categorias = categoriaService.listarTodas()
        .stream() // esto permite procesar la lista de categorias
        .map(MapperCategoria::toDTO).toList(); // y esto convierte las entidades a DTOs

        return ResponseEntity.ok(categorias);
    }

    // listar con paginacion
    @GetMapping("/paginacion")
    public ResponseEntity<Page<CategoriaDTO>> listar(Pageable pageable){
        Page<CategoriaDTO> cat = categoriaService.listar(pageable)
        .map(MapperCategoria::toDTO);
        return ResponseEntity.ok(cat);
    }

    // obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id){
        Categoria cat = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(MapperCategoria.toDTO(cat));
    }
    
    

}
