package com.example.uade.tpo.ecommerce_grupo10.entity.cart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Items Carrito")
@Getter // genera los getters
@Setter // genera los setters
@NoArgsConstructor // genera el constructor por defecto
@AllArgsConstructor // genera el constructor con todos los parametros
public class ItemCarrito {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private int cantidad;

    @Column(nullable=false)
    private double precioUnitario;
    
}
