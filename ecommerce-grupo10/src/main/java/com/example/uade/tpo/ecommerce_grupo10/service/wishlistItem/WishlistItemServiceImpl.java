package com.example.uade.tpo.ecommerce_grupo10.service.wishlistItem;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.WishlistItemDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__.MapperWishlistItem;
import com.example.uade.tpo.ecommerce_grupo10.entity.wishlist.Wishlist;
import com.example.uade.tpo.ecommerce_grupo10.entity.wishlist.WishlistItem;
import com.example.uade.tpo.ecommerce_grupo10.repository.ProductoRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.WishlistItemRepository;
import com.example.uade.tpo.ecommerce_grupo10.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistItemServiceImpl implements WishlistItemService{
    
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository itemRepository;
    private final ProductoRepository productoRepository;
    private final MapperWishlistItem mapperWishlistItem;

    @Override
    @Transactional(readOnly = true)
    public List<WishlistItemDTO> listItems(Long usuarioId) {
        Wishlist wishlist = wishlistRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist no encontrada para el usuario"));
        return itemRepository.findAllByWishlistId(wishlist.getId())
                .stream() // lo convierto a stream
                .map(mapperWishlistItem::toDTO) // lo convierto a DTO
                .toList(); // y por ultimo a lista para devolver
    }

    @Override
    public WishlistItemDTO addItem(Long usuarioId, Long productoId) {
        Wishlist wishlist = wishlistRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist no encontrada para el usuario"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        boolean exists = itemRepository.existsByWishlistIdAndProductoId(wishlist.getId(), productoId);
        if (exists) { // hago la verificacion para ver si el producto ya existe en la wishlist
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El producto ya está en la wishlist");
        }

        WishlistItem it = new WishlistItem();
        it.setWishlist(wishlist);
        it.setProducto(producto);
        // agregadoA se setea solo por default en la entidad
        it = itemRepository.save(it);
        return mapperWishlistItem.toDTO(it);
    }

    @Override
    public void removeItem(Long usuarioId, Long productoId) {
        Wishlist wishlist = wishlistRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist no encontrada para el usuario"));

        if (!itemRepository.existsByWishlistIdAndProductoId(wishlist.getId(), productoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item no encontrado en la wishlist");
        }
        itemRepository.deleteByWishlistIdAndProductoId(wishlist.getId(), productoId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long usuarioId, Long productoId) {
        Wishlist wishlist = wishlistRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist no encontrada para el usuario"));
        return itemRepository.existsByWishlistIdAndProductoId(wishlist.getId(), productoId);
    }

}
