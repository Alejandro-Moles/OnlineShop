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

    @GetMapping("/createCart")
    public List<CartItemModel> createCart(HttpSession session){
        if(cartService.getUserLoggedMail() != null){
            return cartService.createCartToUser(session, cartService.getUserLoggedMail());
        }
        return cartService.createCart(session);
    }

    @GetMapping("/getCart")
    public List<CartItemModel> getCart(HttpSession session){
        if(cartService.getUserLoggedMail() != null){
            return cartService.getCartToUser(session, cartService.getUserLoggedMail());
        }
        return cartService.getCart(session);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartRequestDTO, HttpSession session) {
        try {
            if(cartService.getUserLoggedMail() != null){
                cartService.addProductToCartToUser(cartRequestDTO, session, cartService.getUserLoggedMail());
                return ResponseEntity.ok("Producto agregado al carrito del usuario");
            }
            cartService.addProductToCart(cartRequestDTO, session);
            return ResponseEntity.ok("Producto agregado al carrito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto al carrito " + e);
        }
    }

}
