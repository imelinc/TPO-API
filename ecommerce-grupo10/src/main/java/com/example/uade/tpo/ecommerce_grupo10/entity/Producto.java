package com.example.uade.tpo.ecommerce_grupo10.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.uade.tpo.ecommerce_grupo10.entity.cart.ItemCarrito;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Productos")
@Getter // genera los getters
@Setter // genera los setters
@NoArgsConstructor // genera el constructor por defecto
@AllArgsConstructor // genera el constructor con todos los parametros
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 500) // las descripciones pueden ser largas
    private String descripcion;

    @Column(nullable = false) 
    private double precio;

    @Column(nullable=false)
    private int stock;

    @ManyToOne
    private Usuario vendedor; // Relacion muchos a uno con la entidad Usuario, cada producto tiene un vendedor asociado

    @ManyToOne
    private Categoria categoria; // Relacion muchos a uno con la entidad Categoria, cada producto pertenece a una categoria

    @OneToMany(mappedBy="producto") // relacion uno a muchos con la entidad ImagenProducto
    private Set<ImagenProducto> imagenes = new HashSet<>(); // Set de imagenes

    @OneToOne(mappedBy="producto") // relacion uno a uno con la entidad DescuentoProducto
    private DescuentoProducto descuento; // Cada producto puede tener un descuento asociado (los descuentos no son acumulables)

    @OneToMany(mappedBy = "producto") // relacion uno a muchos con la entidad ItemCarrito
    private Set<ItemCarrito> itemsCarrito = new HashSet<>(); // Set de items del carrito asociados a este producto
}
