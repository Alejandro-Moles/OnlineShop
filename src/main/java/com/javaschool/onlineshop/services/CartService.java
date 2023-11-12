package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CartDTO;
import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.models.CartItem;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductsService productsService;

    public void addProductToCart(CartDTO cartRequestDTO , HttpSession session){
        System.out.println(session);
        UUID productUuid = cartRequestDTO.getProductUuid();
        ProductRequestDTO product = productsService.getProductsbyUuid(productUuid);
        List<CartItem> productCart = createCart(session);

        Optional<CartItem> existingCartItem = productCart.stream()
                .filter(item -> item.getProductUuid().equals(productUuid))
                .findFirst();

        if (existingCartItem.isPresent()) {
            existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + 1);
        } else {
            CartItem newCartItem = addCartItem(product);
            productCart.add(newCartItem);
        }

    }

    private List<CartItem> createCart(HttpSession session){
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private CartItem addCartItem(ProductRequestDTO productDTO){
        CartItem cartItem = new CartItem();
        cartItem.setProductUuid(productDTO.getUuid());
        cartItem.setTitle(productDTO.getTitle());
        cartItem.setPrice(productDTO.getPrice());
        cartItem.setQuantity(1);

        return cartItem;
    }
}
