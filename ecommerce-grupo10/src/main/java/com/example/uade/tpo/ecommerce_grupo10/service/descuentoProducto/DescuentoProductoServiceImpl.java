package com.example.uade.tpo.ecommerce_grupo10.service.descuentoProducto;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.uade.tpo.ecommerce_grupo10.entity.DescuentoProducto;
import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.DescuentoProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__.MapperDescuentoProducto;
import com.example.uade.tpo.ecommerce_grupo10.exceptions.RecursoNoEncontrado;
import com.example.uade.tpo.ecommerce_grupo10.repository.DescuentoProductoRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DescuentoProductoServiceImpl implements DescuentoProductoService {

    private final DescuentoProductoRepository descuentoProductoRepository;
    private final ProductoRepository productoRepository;
    private final MapperDescuentoProducto mapperDescuentoProducto;

    // crear descuento
    @Override
    public DescuentoProductoDTO crear(Long productoId, DescuentoProductoDTO dto) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontrado("Producto no encontrado id=" + productoId));

        validarDTO(dto); // validamos la entidad del descuento para ver si es valido

        DescuentoProducto entity = descuentoProductoRepository.findByProductoId(productoId)
                .orElse(mapperDescuentoProducto.toEntity(dto, producto));

        if (entity.getId() != null) { // si ya existe un descuento para este producto, actualizamos
            mapperDescuentoProducto.updateEntityFromDTO(dto, entity, producto);
        }

        DescuentoProducto guardado = descuentoProductoRepository.save(entity);
        return mapperDescuentoProducto.toDTO(guardado);
    }

    // obtener por id
    @Override
    @Transactional(readOnly = true)
    public DescuentoProductoDTO obtenerPorId(Long id) {
        return descuentoProductoRepository.findById(id)
                .map(mapperDescuentoProducto::toDTO)
                .orElseThrow(() -> new RecursoNoEncontrado("Descuento no encontrado id=" + id));
    }

    // obtener los descuentos por producto
    @Override
    @Transactional(readOnly = true)
    public DescuentoProductoDTO obtenerPorProducto(Long productoId) {
        return descuentoProductoRepository.findByProductoId(productoId)
                .map(mapperDescuentoProducto::toDTO)
                .orElseThrow(
                        () -> new RecursoNoEncontrado("El producto id=" + productoId + " no tiene descuento"));
    }

    // obtener los descuentos por producto sin lanzar excepción
    @Override
    @Transactional(readOnly = true)
    public Optional<DescuentoProductoDTO> obtenerPorProductoOptional(Long productoId) {
        return descuentoProductoRepository.findByProductoId(productoId)
                .map(mapperDescuentoProducto::toDTO);
    }

    // listar con paginacion
    @Override
    @Transactional(readOnly = true)
    public Page<DescuentoProductoDTO> listar(Pageable pageable) {
        return descuentoProductoRepository.findAll(pageable).map(mapperDescuentoProducto::toDTO);
    }

    // actualizar un descuento
    @Override
    public DescuentoProductoDTO actualizar(Long id, DescuentoProductoDTO dto) {
        validarDTO(dto); // primero lo validamos
        DescuentoProducto entity = descuentoProductoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Descuento no encontrado id=" + id));

        Producto producto = entity.getProducto();
        if (dto.getProductoId() != null && !dto.getProductoId().equals(producto.getId())) {
            producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(
                            () -> new RecursoNoEncontrado("Producto no encontrado id=" + dto.getProductoId()));
        }

        mapperDescuentoProducto.updateEntityFromDTO(dto, entity, producto);
        return mapperDescuentoProducto.toDTO(descuentoProductoRepository.save(entity));
    }

    @Override
    public DescuentoProductoDTO activar(Long id) {
        DescuentoProducto entity = descuentoProductoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Descuento no encontrado id=" + id));
        entity.setActivo(true);
        return mapperDescuentoProducto.toDTO(descuentoProductoRepository.save(entity));
    }

    @Override
    public DescuentoProductoDTO desactivar(Long id) {
        DescuentoProducto entity = descuentoProductoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Descuento no encontrado id=" + id));
        entity.setActivo(false);
        return mapperDescuentoProducto.toDTO(descuentoProductoRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        if (!descuentoProductoRepository.existsById(id)) {
            throw new RecursoNoEncontrado("Descuento no encontrado id=" + id);
        }
        descuentoProductoRepository.deleteById(id);
    }

    // Validar que el usuario sea propietario del producto
    @Override
    public void validarPropietarioProducto(Long productoId, String emailUsuario) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontrado("Producto no encontrado id=" + productoId));

        if (producto.getVendedor() == null) {
            throw new IllegalArgumentException("El producto no tiene un vendedor asignado");
        }

        if (!producto.getVendedor().getEmail().equals(emailUsuario)) {
            throw new IllegalArgumentException("No tienes permisos para gestionar descuentos de este producto");
        }
    }

    // Validar que el usuario sea propietario del producto asociado al descuento
    @Override
    public void validarPropietarioDescuento(Long descuentoId, String emailUsuario) {
        DescuentoProducto descuento = descuentoProductoRepository.findById(descuentoId)
                .orElseThrow(() -> new RecursoNoEncontrado("Descuento no encontrado id=" + descuentoId));

        Producto producto = descuento.getProducto();
        if (producto == null) {
            throw new IllegalArgumentException("El descuento no tiene un producto asociado");
        }

        if (producto.getVendedor() == null) {
            throw new IllegalArgumentException("El producto no tiene un vendedor asignado");
        }

        if (!producto.getVendedor().getEmail().equals(emailUsuario)) {
            throw new IllegalArgumentException("No tienes permisos para gestionar este descuento");
        }
    }

    // metodo para validar si el descuento es valido en terminos de valores y fechas
    private void validarDTO(DescuentoProductoDTO dto) {
        if (dto.getPorcentajeDescuento() == null || dto.getPorcentajeDescuento() < 0
                || dto.getPorcentajeDescuento() > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }
        Date ini = dto.getFechaInicio();
        Date fin = dto.getFechaFin();
        if (ini == null || fin == null || !fin.after(ini)) {
            throw new IllegalArgumentException("Rango de fechas inválido (fin debe ser posterior a inicio)");
        }
    }
}
