package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.CartDTO;
import com.javaschool.onlineshop.dto.ProductRequestDTO;
import com.javaschool.onlineshop.models.CartItemModel;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    @Getter
    @Setter
    private String userLoggedMail;

    public void addProductToCart(CartDTO cartRequestDTO , HttpSession session){
        UUID productUuid = cartRequestDTO.getProductUuid();
        ProductRequestDTO product = productsService.getProductsbyUuid(productUuid);
        List<CartItemModel> productCart = getCart(session);
        updateCartWithProduct(productCart, productUuid, product);
    }

    public List<CartItemModel> createCart(HttpSession session){
        List<CartItemModel> cart = (List<CartItemModel>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private CartItemModel addCartItem(ProductRequestDTO productDTO){
        CartItemModel cartItem = new CartItemModel();
        cartItem.setProductUuid(productDTO.getUuid());
        cartItem.setTitle(productDTO.getTitle());
        cartItem.setPrice(productDTO.getPrice());
        cartItem.setQuantity(1);

        return cartItem;
    }

    public List<CartItemModel> getCart(HttpSession session){
        List<CartItemModel> cart = (List<CartItemModel>) session.getAttribute("cart");
        if (cart == null) {
            System.out.println("No se encuentra el carro");
        }
        return cart;
    }

    public List<CartItemModel> getCartToUser(HttpSession session, String userMail){
        List<CartItemModel> cart = (List<CartItemModel>) session.getAttribute("cart_" + userMail);
        if (cart == null) {
            System.out.println("No se encuentra el carro");
        }
        return cart;
    }

    public List<CartItemModel> createCartToUser(HttpSession session, String userMail){
        List<CartItemModel> cartForUser = (List<CartItemModel>) session.getAttribute("cart_" + userMail);
        if (cartForUser == null) {
            cartForUser = new ArrayList<>();
            session.setAttribute("cart_" + userMail, cartForUser);
        }
        return cartForUser;
    }

    public void addProductToCartToUser(CartDTO cartRequestDTO , HttpSession session, String mail){
        UUID productUuid = cartRequestDTO.getProductUuid();
        ProductRequestDTO product = productsService.getProductsbyUuid(productUuid);
        List<CartItemModel> productCart = getCartToUser(session, mail);
        updateCartWithProduct(productCart, productUuid, product);
    }

    public void syncCartToUser(HttpSession session, String mail){
        List<CartItemModel> cart = createCart(session);
        List<CartItemModel> userCart = createCartToUser(session, mail);
        if (cart.size() != 0) {
            for (CartItemModel cartItem : cart) {
                Optional<CartItemModel> existingCartItem = userCart.stream()
                        .filter(item -> item.getProductUuid().equals(cartItem.getProductUuid()))
                        .findFirst();
                if (existingCartItem.isPresent()) {
                    existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + cartItem.getQuantity());
                } else {
                    userCart.add(cartItem);
                }
            }
        }
        session.setAttribute("cart_" + mail, userCart);
    }

    private void updateCartWithProduct(List<CartItemModel> cart, UUID productUuid, ProductRequestDTO product) {
        Optional<CartItemModel> existingCartItem = cart.stream()
                .filter(item -> item.getProductUuid().equals(productUuid))
                .findFirst();

        if (existingCartItem.isPresent()) {
            existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + 1);
        } else {
            CartItemModel newCartItem = addCartItem(product);
            cart.add(newCartItem);
        }
    }

    public void updateProductCart (List<CartItemModel>  cartItemModels, HttpSession session){
        System.out.println(cartItemModels);
        session.setAttribute("cart", cartItemModels);
    }

    public void updateProductCartForUser (List<CartItemModel>  cartItemModels, String mail, HttpSession session){
        System.out.println(cartItemModels);
        System.out.println(mail);
        session.setAttribute("cart_" + mail, cartItemModels);
    }
}
