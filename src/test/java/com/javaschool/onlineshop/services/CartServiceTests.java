package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CartDTO;
import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.models.CartItemModel;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTests {
    private final ProductsService productsServiceMock = Mockito.mock(ProductsService.class);
    private final CartService cartService = new CartService(productsServiceMock);

    /**
     * Test for adding a product to the cart, checking if it successfully adds the product to the cart.
     */
    @Test
    void addProductToCart_ShouldAddProductToCart() {
        // Mocking data
        HttpSession sessionMock = Mockito.mock(HttpSession.class);

        UUID productUuid = UUID.randomUUID();
        ProductRequestDTO product = new ProductRequestDTO();
        product.setUuid(productUuid);
        product.setTitle("Test Product");
        product.setPrice(19.99);

        CartDTO cartRequestDTO = new CartDTO();
        cartRequestDTO.setProductUuid(productUuid);

        // Simulating that the cart already exists in the session
        List<CartItemModel> cart = new ArrayList<>();
        when(sessionMock.getAttribute("cart")).thenReturn(cart);

        when(productsServiceMock.getProductsbyUuid(productUuid)).thenReturn(product);

        // Calling the service method
        cartService.addProductToCart(cartRequestDTO, sessionMock);

        // Verifying that setAttribute was not called
        verify(sessionMock, never()).setAttribute(eq("cart"), any());

        // Assertions
        assertEquals(1, cart.size());

        CartItemModel cartItem = cart.get(0);
        assertEquals(productUuid, cartItem.getProductUuid());
        assertEquals("Test Product", cartItem.getTitle());
        assertEquals(19.99, cartItem.getPrice());
        assertEquals(1, cartItem.getQuantity());
    }

    /**
     * Test for adding a product to the user's cart, checking if it successfully adds the product to the user's cart.
     */
    @Test
    void addProductToCartToUser_ShouldAddProductToUserCart() {
        // Mocking data
        HttpSession sessionMock = Mockito.mock(HttpSession.class);

        String userMail = "test@example.com";
        UUID productUuid = UUID.randomUUID();
        ProductRequestDTO product = new ProductRequestDTO();
        product.setUuid(productUuid);
        product.setTitle("Test Product");
        product.setPrice(19.99);

        CartDTO cartRequestDTO = new CartDTO();
        cartRequestDTO.setProductUuid(productUuid);

        // Simulating that the user's cart already exists in the session
        List<CartItemModel> userCart = new ArrayList<>();
        when(sessionMock.getAttribute("cart_" + userMail)).thenReturn(userCart);

        when(productsServiceMock.getProductsbyUuid(productUuid)).thenReturn(product);

        // Calling the service method
        cartService.addProductToCartToUser(cartRequestDTO, sessionMock, userMail);

        // Verifying that setAttribute was not called
        verify(sessionMock, never()).setAttribute(eq("cart_" + userMail), any());

        // Assertions
        assertEquals(1, userCart.size());

        CartItemModel cartItem = userCart.get(0);
        assertEquals(productUuid, cartItem.getProductUuid());
        assertEquals("Test Product", cartItem.getTitle());
        assertEquals(19.99, cartItem.getPrice());
        assertEquals(1, cartItem.getQuantity());
    }

}
