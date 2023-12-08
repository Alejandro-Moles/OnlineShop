package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CartDTO;
import com.javaschool.onlineshop.models.CartItemModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.services.CartService;
import com.javaschool.onlineshop.services.ShopUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Endpoint to create a cart (either for a logged-in user or as a guest)
    @GetMapping("/createCart")
    public List<CartItemModel> createCart(HttpSession session) {
        if (cartService.getUserLoggedMail() != null) {
            return cartService.createCartToUser(session, cartService.getUserLoggedMail());
        }
        return cartService.createCart(session);
    }

    // Endpoint to retrieve the current cart (either for a logged-in user or as a guest)
    @GetMapping("/getCart")
    public List<CartItemModel> getCart(HttpSession session) {
        if (cartService.getUserLoggedMail() != null) {
            return cartService.getCartToUser(session, cartService.getUserLoggedMail());
        }
        return cartService.getCart(session);
    }

    // Endpoint to add a product to the cart
    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartRequestDTO, HttpSession session) {
        try {
            if (cartService.getUserLoggedMail() != null) {
                cartService.addProductToCartToUser(cartRequestDTO, session, cartService.getUserLoggedMail());
                return ResponseEntity.ok("Product added to the user's cart");
            }
            cartService.addProductToCart(cartRequestDTO, session);
            return ResponseEntity.ok("Product added to the cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding the product to the cart: " + e);
        }
    }

    // Endpoint to update the cart with a list of cart items
    @PostMapping("/cart/update")
    public ResponseEntity<String> updateCart(@RequestBody List<CartItemModel> cartItemModels, HttpSession session) {
        try {
            if (cartService.getUserLoggedMail() != null) {
                cartService.updateProductCartForUser(cartItemModels, cartService.getUserLoggedMail(), session);
                return ResponseEntity.ok("Cart updated for the user");
            }
            cartService.updateProductCart(cartItemModels, session);
            return ResponseEntity.ok("Cart updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating the cart: " + e);
        }
    }

    // Endpoint to clear the cart (remove all items)
    @PostMapping("/cart/clear")
    public ResponseEntity<String> clearCart(HttpSession session) {
        try {
            cartService.clearUserCart(session, cartService.getUserLoggedMail());
            return ResponseEntity.ok("Cart cleared for the user");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error clearing the cart: " + e);
        }
    }
}
