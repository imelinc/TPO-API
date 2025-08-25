package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__;

public class CategoriaDTO {
    private Long id;
    private String nombre;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
