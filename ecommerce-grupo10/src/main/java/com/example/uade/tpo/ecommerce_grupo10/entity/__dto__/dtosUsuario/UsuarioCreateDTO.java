package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.dtosUsuario;

import com.example.uade.tpo.ecommerce_grupo10.entity.Rol;

import lombok.Data;

@Data
public class UsuarioCreateDTO {
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private Rol rol;
}
