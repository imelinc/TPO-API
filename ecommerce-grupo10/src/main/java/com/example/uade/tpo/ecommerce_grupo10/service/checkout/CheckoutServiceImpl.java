package com.example.uade.tpo.ecommerce_grupo10.service.checkout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.uade.tpo.ecommerce_grupo10.entity.ItemOrden;
import com.example.uade.tpo.ecommerce_grupo10.entity.Orden;
import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.Usuario;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.CarritoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ItemCarritoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.OrdenDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__.MapperOrden;
import com.example.uade.tpo.ecommerce_grupo10.exceptions.RecursoNoEncontrado;
import com.example.uade.tpo.ecommerce_grupo10.repository.ItemOrdenRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.OrdenRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.ProductoRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.UsuarioRepository;
import com.example.uade.tpo.ecommerce_grupo10.service.carrito.CarritoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    private final CarritoService carritoService;
    private final OrdenRepository ordenRepository;
    private final ItemOrdenRepository itemOrdenRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final MapperOrden mapperOrden;

    @Override
    public OrdenDTO realizarCheckout(Long usuarioId) {
        // Validar que se pueda realizar checkout
        if (!validarCheckout(usuarioId)) {
            throw new IllegalStateException("No se puede realizar el checkout");
        }

        // Obtener carrito del usuario
        CarritoDTO carritoDTO = carritoService.obtenerPorUsuario(usuarioId);
        if (carritoDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }

        // Validar stock de productos
        validarStock(carritoDTO);

        // Crear la orden
        Orden orden = crearOrdenDesdeCarrito(usuarioId, carritoDTO);

        // Vaciar el carrito
        carritoService.vaciar(usuarioId);

        // Retornar la orden creada
        return mapperOrden.toDTO(orden);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validarCheckout(Long usuarioId) {
        try {
            // Verificar que el usuario existe
            if (!usuarioRepository.existsById(usuarioId)) {
                return false;
            }

            // Verificar que tiene carrito y items
            CarritoDTO carrito = carritoService.obtenerPorUsuario(usuarioId);
            if (carrito.getItems().isEmpty()) {
                return false;
            }

            // Verificar stock disponible
            for (ItemCarritoDTO item : carrito.getItems()) {
                Producto producto = productoRepository.findById(item.getProductoId())
                        .orElse(null);
                if (producto == null || producto.getStock() < item.getCantidad()) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validarStock(CarritoDTO carrito) {
        List<String> errores = new ArrayList<>();

        for (ItemCarritoDTO item : carrito.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontrado("Producto no encontrado: " + item.getProductoId()));

            if (producto.getStock() < item.getCantidad()) {
                errores.add(String.format("Stock insuficiente para %s. Disponible: %d, Solicitado: %d",
                        producto.getTitulo(), producto.getStock(), item.getCantidad()));
            }
        }

        if (!errores.isEmpty()) {
            throw new IllegalArgumentException("Errores de stock: " + String.join(", ", errores));
        }
    }

    private Orden crearOrdenDesdeCarrito(Long usuarioId, CarritoDTO carrito) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontrado("Usuario no encontrado"));

        // Crear orden
        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setFechaCreacion(LocalDateTime.now());
        orden.setEstado("PENDIENTE");
        orden.setTotal(carrito.getTotal());

        // Guardar orden primero para obtener ID
        orden = ordenRepository.save(orden);

        // Crear items de orden
        for (ItemCarritoDTO itemCarrito : carrito.getItems()) {
            Producto producto = productoRepository.findById(itemCarrito.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontrado("Producto no encontrado"));

            ItemOrden itemOrden = new ItemOrden();
            itemOrden.setOrden(orden);
            itemOrden.setIdProducto(producto.getId());
            itemOrden.setTitulo(producto.getTitulo());
            itemOrden.setCantidad(itemCarrito.getCantidad());
            itemOrden.setPrecioUnitario(itemCarrito.getPrecioUnitario());
            itemOrden.setDescuentoAplicado(0.0); // Por ahora sin descuentos

            itemOrdenRepository.save(itemOrden);

            // Actualizar stock del producto
            producto.setStock(producto.getStock() - itemCarrito.getCantidad());
            productoRepository.save(producto);
        }

        // Recargar orden con items
        return ordenRepository.findById(orden.getId())
                .orElseThrow(() -> new RecursoNoEncontrado("Error al crear orden"));
    }
}
