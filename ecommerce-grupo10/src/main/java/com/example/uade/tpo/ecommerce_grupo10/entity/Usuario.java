package com.example.uade.tpo.ecommerce_grupo10.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.uade.tpo.ecommerce_grupo10.entity.cart.Carrito;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // indica que es una entidad de JPA
@Table(name = "Usuarios") // Le cambiamos el nombre a la tabla a "Usuarios"
@Getter // genera todos los getters
@Setter // genera todos los setters
@NoArgsConstructor // genera el constructor por defecto
@AllArgsConstructor // genera el constructor con todos los parametros
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generacion del ID automaticamente
    private Long id;

    @Column(nullable= false, length=100) // columna no puede ser nula y es un VARCHAR de 100 caracteres
    private String username;

    @Column(nullable= false, length=100) // columna no puede ser nula y es un VARCHAR de 100 caracteres
    private String password;

    @Column(nullable= false, length=100) // columna no puede ser nula y es un VARCHAR de 100 caracteres
    private String nombreCompleto;

    @Column(nullable= false, length=100) // columna no puede ser nula y es un VARCHAR de 100 caracteres
    private String email;

    @Column(nullable= false, length=100) // columna no puede ser nula y es un VARCHAR de 100 caracteres
    private String telefono;

    @Column(nullable= false, length=100) // columna no puede ser nula y es un VARCHAR de 100 caracteres
    private String direccion;

    @ManyToMany(mappedBy= "usuario") // relacion muchos a muchos con la entidad RolUsuario
    private Set<RolUsuario> roles = new HashSet<>(); // Set de roles del usuario, inicializado como un HashSet

    @OneToOne(mappedBy= "usuario") 
    private Carrito carrito; // Relacion uno a uno con la entidad Carrito, cada usuario tiene un carrito asociado

    // si el usuario es vendedor, puede tener productos asociados
    @OneToMany(mappedBy = "vendedor")
    private Set<Producto> productos = new HashSet<>(); // Set de productos asociados
}
