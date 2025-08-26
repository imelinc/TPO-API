package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.dtosUsuario;

import java.util.Set;

import com.example.uade.tpo.ecommerce_grupo10.entity.Rol;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String direccion;
    private Set<Rol> roles;
}
