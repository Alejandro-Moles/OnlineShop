package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.CartDTO;
import com.javaschool.onlineshop.models.CartItem;
import com.javaschool.onlineshop.services.CartService;
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

    @GetMapping("/cart")
    public List<CartItem> getCart(HttpSession session){
        System.out.println(session);
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        System.out.println(cart);
        if(cart == null){
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        System.out.println(cart);
        return cart;
    }

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartRequestDTO, HttpSession session) {
        try {
            cartService.addProductToCart(cartRequestDTO, session);
            return ResponseEntity.ok("Producto agregado al carrito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto al carrito " + e);
        }
    }

}
