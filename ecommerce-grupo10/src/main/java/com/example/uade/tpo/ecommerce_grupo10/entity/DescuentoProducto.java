package com.example.uade.tpo.ecommerce_grupo10.entity;

import java.util.Date;

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
@Table(name="Descuentos Productos")
@Getter // genera los getters
@Setter // genera los setters
@NoArgsConstructor // genera el constructor por defecto
@AllArgsConstructor // genera el constructor con todos los parametros
public class DescuentoProducto {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private double porcentajeDescuento;

    @Column(nullable=false)
    private Date fechaInicio;

    @Column(nullable=false)
    private Date fechaFin;

    @Column(nullable=false)
    private boolean activo;
}
