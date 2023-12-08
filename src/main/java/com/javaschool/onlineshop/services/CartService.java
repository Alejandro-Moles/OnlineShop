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
public class CartService {

    // ProductService is used to retrieve product details
    private final ProductsService productsService;

    // The logged-in user's email, set by the controller
    @Getter
    @Setter
    private String userLoggedMail;

    // This method creates a new cart in the session if it doesn't exist
    public List<CartItemModel> createCart(HttpSession session) {
        List<CartItemModel> cart = (List<CartItemModel>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    // This method creates a new CartItemModel for a product
    private CartItemModel addCartItem(ProductRequestDTO productDTO) {
        CartItemModel cartItem = new CartItemModel();
        cartItem.setProductUuid(productDTO.getUuid());
        cartItem.setTitle(productDTO.getTitle());
        cartItem.setPrice(productDTO.getPrice());
        cartItem.setQuantity(1);
        return cartItem;
    }

    // This method retrieves the general cart from the session
    public List<CartItemModel> getCart(HttpSession session) {
        List<CartItemModel> cart = (List<CartItemModel>) session.getAttribute("cart");
        if (cart == null) {
            System.out.println("Cart not found");
        }
        return cart;
    }

    // This method retrieves the user-specific cart from the session
    public List<CartItemModel> getCartToUser(HttpSession session, String userMail) {
        List<CartItemModel> cart = (List<CartItemModel>) session.getAttribute("cart_" + userMail);
        if (cart == null) {
            System.out.println("Cart not found");
        }
        return cart;
    }

    // This method creates a new user-specific cart in the session if it doesn't exist
    public List<CartItemModel> createCartToUser(HttpSession session, String userMail) {
        List<CartItemModel> cartForUser = (List<CartItemModel>) session.getAttribute("cart_" + userMail);
        if (cartForUser == null) {
            cartForUser = new ArrayList<>();
            session.setAttribute("cart_" + userMail, cartForUser);
        }
        return cartForUser;
    }

    // This method synchronizes the general cart to the user-specific cart in the session
    public void syncCartToUser(HttpSession session, String mail) {
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

    // This method updates the general cart with a product
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

    // This method updates the general cart in the session
    public void updateProductCart(List<CartItemModel> cartItemModels, HttpSession session) {
        session.setAttribute("cart", cartItemModels);
    }

    // This method updates the user-specific cart in the session
    public void updateProductCartForUser(List<CartItemModel> cartItemModels, String mail, HttpSession session) {
        session.setAttribute("cart_" + mail, cartItemModels);
    }

    // This method clears the user-specific cart in the session
    public void clearUserCart(HttpSession session, String userMail) {
        List<CartItemModel> cart = new ArrayList<>();
        session.setAttribute("cart_" + userMail, cart);
        session.removeAttribute("cart_" + userMail);
    }

    // This method adds a product to the general cart in the session
    @Transactional(readOnly = true)
    public void addProductToCart(CartDTO cartRequestDTO, HttpSession session) {
        UUID productUuid = cartRequestDTO.getProductUuid();
        ProductRequestDTO product = productsService.getProductsbyUuid(productUuid);
        List<CartItemModel> productCart = getCart(session);
        updateCartWithProduct(productCart, productUuid, product);
    }

    // This method adds a product to the user-specific cart in the session
    @Transactional(readOnly = true)
    public void addProductToCartToUser(CartDTO cartRequestDTO, HttpSession session, String mail) {
        UUID productUuid = cartRequestDTO.getProductUuid();
        ProductRequestDTO product = productsService.getProductsbyUuid(productUuid);
        List<CartItemModel> productCart = getCartToUser(session, mail);
        updateCartWithProduct(productCart, productUuid, product);
    }
}
