package com.example.uade.tpo.ecommerce_grupo10.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.uade.tpo.ecommerce_grupo10.entity.cart.Carrito;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

    @ManyToMany(fetch=FetchType.EAGER) // relacion muchos a muchos con la entidad RolUsuario, cargado de forma eager
    // Creo la tabla intermedia de usuarios y roles ya que es una relacion muchos a muchos
    @JoinTable(name = "usuarios_roles", 
               joinColumns = @JoinColumn(name = "usuario_id"), 
               inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>(); // Set de roles del usuario, inicializado como un HashSet

    @OneToOne(mappedBy= "usuario") 
    private Carrito carrito; // Relacion uno a uno con la entidad Carrito, cada usuario tiene un carrito asociado

    // si el usuario es vendedor, puede tener productos asociados
    @OneToMany(mappedBy = "vendedor")
    private Set<Producto> productos = new HashSet<>(); // Set de productos asociados

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true) // relacion uno a muchos con la entidad Orden
    // cascade = CascadeType.ALL permite que cualquier operacion de usuario, se aplique a sus ordens asociadas (borrar, actualizar, etc.)
    // orphanRemoval = true permite que al eliminar una orden de un usuario,
    // se elimine la orden de la base de datos si ya no tiene un usuario asociado
    private Set<Orden> compras = new HashSet<>();


}
