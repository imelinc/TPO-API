package com.example.uade.tpo.ecommerce_grupo10.controllers.auth.dto;

import com.example.uade.tpo.ecommerce_grupo10.entity.Rol;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private Rol rol;
}
