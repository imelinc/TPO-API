package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.dtosUsuario;

import com.example.uade.tpo.ecommerce_grupo10.entity.Rol;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private Rol rol;
}
