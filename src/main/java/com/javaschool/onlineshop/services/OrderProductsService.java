package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.OrderProductsRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.OrderProductsMapper;
import com.javaschool.onlineshop.models.Order;
import com.javaschool.onlineshop.models.OrderProducts;
import com.javaschool.onlineshop.models.Products;
import com.javaschool.onlineshop.repositories.OrderProductsRepository;
import com.javaschool.onlineshop.repositories.OrderRepository;
import com.javaschool.onlineshop.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderProductsService {
    private final OrderRepository orderRepository;
    private final ProductsRepository productsRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final OrderProductsMapper orderProductsMapper;


    public OrderProductsRequestDTO saveOrderProducts(OrderProductsRequestDTO orderProductsDTO){
        OrderProducts orderProducts = createOrderProductsEntity(orderProductsDTO,new OrderProducts());
        if(orderProductsRepository.existsByOrderAndProduct(orderProducts.getOrder(), orderProducts.getProduct())){
            throw new ResourceDuplicate("Order already have with this product");
        }
        orderProductsRepository.save(orderProducts);
        return createOrderProductsDTO(orderProducts);
    }

    @Transactional(readOnly = true)
    private Order findOrderByUUID(UUID uuid){
        return orderRepository.findById(uuid).orElseThrow(() -> new NoExistData("This order don't exist"));
    }

    @Transactional(readOnly = true)
    private Products findProducts(String title){
        return productsRepository.findByTitle(title).orElseThrow(() -> new NoExistData("This product don't exist"));
    }

    private OrderProductsRequestDTO createOrderProductsDTO(OrderProducts orderProducts){
        return orderProductsMapper.createOrderProductsDTO(orderProducts);
    }

    private OrderProducts createOrderProductsEntity(OrderProductsRequestDTO orderProductsDTO, OrderProducts orderProducts){
        orderProducts.setOrder(findOrderByUUID(orderProductsDTO.getOrderUUID()));
        orderProducts.setQuantity(orderProductsDTO.getQuantity());
        orderProducts.setIsDeleted(false);
        orderProducts.setProduct(findProducts(orderProductsDTO.getProductTitle()));
        return orderProducts;
    }
}
