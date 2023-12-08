package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.OrderProductsRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.OrderProductsMapper;
import com.javaschool.onlineshop.models.CountryModel;
import com.javaschool.onlineshop.models.OrderModel;
import com.javaschool.onlineshop.models.OrderProductsModel;
import com.javaschool.onlineshop.models.ProductsModel;
import com.javaschool.onlineshop.repositories.OrderProductsRepository;
import com.javaschool.onlineshop.repositories.OrderRepository;
import com.javaschool.onlineshop.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProductsService {

    private final OrderRepository orderRepository;
    private final ProductsRepository productsRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final OrderProductsMapper orderProductsMapper;

    // This method creates an OrderProductsDTO from an OrderProductsModel
    private OrderProductsRequestDTO createOrderProductsDTO(OrderProductsModel orderProducts) {
        return orderProductsMapper.createOrderProductsDTO(orderProducts);
    }

    // This method updates an OrderProductsModel entity with data from an OrderProductsDTO
    private OrderProductsModel createOrderProductsEntity(OrderProductsRequestDTO orderProductsDTO, OrderProductsModel orderProducts) {
        // Finding the order and product entities by UUID and title, respectively
        orderProducts.setOrder(findOrderByUUID(orderProductsDTO.getOrderUUID()));
        orderProducts.setQuantity(orderProductsDTO.getQuantity());
        orderProducts.setIsDeleted(false);
        orderProducts.setProduct(findProducts(orderProductsDTO.getProductUUID()));
        return orderProducts;
    }

    // This method saves a new order product to the database
    @Transactional
    public OrderProductsRequestDTO saveOrderProducts(OrderProductsRequestDTO orderProductsDTO) {
        // Creating an OrderProductsModel entity from the DTO
        OrderProductsModel orderProducts = createOrderProductsEntity(orderProductsDTO, new OrderProductsModel());

        // Checking if an order already has the same product
        if (orderProductsRepository.existsByOrderAndProduct(orderProducts.getOrder(), orderProducts.getProduct())) {
            throw new ResourceDuplicate("Order already has this product");
        }

        // Saving the order product to the database
        orderProductsRepository.save(orderProducts);

        // Returning the saved order product as a DTO
        return createOrderProductsDTO(orderProducts);
    }

    // This method finds an order by its UUID
    @Transactional(readOnly = true)
    private OrderModel findOrderByUUID(UUID uuid) {
        return orderRepository.findById(uuid).orElseThrow(() -> new NoExistData("This order doesn't exist"));
    }

    // This method finds a product by its title
    @Transactional(readOnly = true)
    private ProductsModel findProducts(UUID uuid) {
        return productsRepository.findById(uuid).orElseThrow(() -> new NoExistData("This product doesn't exist"));
    }

    // This method finds an order by its UUID
    @Transactional(readOnly = true)
    private OrderModel findOrder(UUID uuid) {
        return orderRepository.findById(uuid).orElseThrow(() -> new NoExistData("This order doesn't exist"));
    }

    // This method retrieves all order products for a given order
    @Transactional(readOnly = true)
    public List<OrderProductsRequestDTO> findAllOrdersProductByOrder(UUID uuid) {
        // Finding the order entity by UUID
        OrderModel orderModel = findOrder(uuid);

        // Retrieving all order products associated with the given order
        return orderProductsRepository.findOrderProductsByOrder(orderModel).stream().map(this::createOrderProductsDTO).toList();
    }
}
