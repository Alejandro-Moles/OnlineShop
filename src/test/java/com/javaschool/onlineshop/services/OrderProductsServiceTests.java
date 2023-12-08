package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.OrderProductsRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.OrderProductsMapper;
import com.javaschool.onlineshop.models.OrderModel;
import com.javaschool.onlineshop.models.OrderProductsModel;
import com.javaschool.onlineshop.models.ProductsModel;
import com.javaschool.onlineshop.repositories.OrderProductsRepository;
import com.javaschool.onlineshop.repositories.OrderRepository;
import com.javaschool.onlineshop.repositories.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class OrderProductsServiceTests {
    private final OrderRepository orderRepositoryMock = Mockito.mock(OrderRepository.class);
    private final ProductsRepository productsRepositoryMock = Mockito.mock(ProductsRepository.class);
    private final OrderProductsRepository orderProductsRepositoryMock = Mockito.mock(OrderProductsRepository.class);
    private final OrderProductsMapper orderProductsMapperMock = Mockito.mock(OrderProductsMapper.class);
    private final OrderProductsService orderProductsService = new OrderProductsService(
            orderRepositoryMock, productsRepositoryMock, orderProductsRepositoryMock, orderProductsMapperMock
    );

    /**
     * Test for saving order products when the order products do not exist, checking if it successfully saves order products.
     */
    @Test
    void saveOrderProducts_WhenOrderProductsDoesNotExist_ShouldSaveOrderProducts() {
        // Mocking data
        when(orderProductsMapperMock.createOrderProductsDTO(any())).thenReturn(new OrderProductsRequestDTO());
        when(orderRepositoryMock.findById(any())).thenReturn(Optional.of(new OrderModel()));
        when(productsRepositoryMock.findByTitle(any())).thenReturn(Optional.of(new ProductsModel()));
        when(orderProductsRepositoryMock.existsByOrderAndProduct(any(OrderModel.class), any(ProductsModel.class)))
                .thenReturn(false);

        // Creating an OrderProductsDTO
        OrderProductsRequestDTO orderProductsDTO = new OrderProductsRequestDTO();
        orderProductsDTO.setOrderUUID(UUID.randomUUID());
        orderProductsDTO.setProductUUID(UUID.randomUUID());
        orderProductsDTO.setQuantity(2);

        // Calling the service method
        OrderProductsRequestDTO savedOrderProductsDTO = orderProductsService.saveOrderProducts(orderProductsDTO);

        // Assertions
        assertNotNull(savedOrderProductsDTO);
    }

    /**
     * Test for saving order products when the order products exist, checking if it throws a ResourceDuplicate exception.
     */
    @Test
    void saveOrderProducts_WhenOrderProductsExists_ShouldThrowResourceDuplicateException() {
        // Mocking data
        when(orderRepositoryMock.findById(any())).thenReturn(Optional.of(new OrderModel()));
        when(productsRepositoryMock.findByTitle(any())).thenReturn(Optional.of(new ProductsModel()));
        when(orderProductsRepositoryMock.existsByOrderAndProduct(any(OrderModel.class), any(ProductsModel.class)))
                .thenReturn(true);

        // Creating an OrderProductsDTO
        OrderProductsRequestDTO orderProductsDTO = new OrderProductsRequestDTO();
        orderProductsDTO.setOrderUUID(UUID.randomUUID());
        orderProductsDTO.setProductUUID(UUID.randomUUID());
        orderProductsDTO.setQuantity(1);

        // Assertions
        assertThrows(ResourceDuplicate.class, () -> orderProductsService.saveOrderProducts(orderProductsDTO));
    }

    /**
     * Test for finding all order products by order, checking if it returns a list of OrderProductsRequestDTO.
     */
    @Test
    void findAllOrdersProductByOrder_ShouldReturnOrderProductsList() {
        // Mocking data
        UUID orderUUID = UUID.randomUUID();
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderUuid(orderUUID);
        when(orderRepositoryMock.findById(orderUUID)).thenReturn(Optional.of(orderModel));
        when(orderProductsRepositoryMock.findOrderProductsByOrder(orderModel))
                .thenReturn(List.of(new OrderProductsModel(), new OrderProductsModel()));

        // Calling the service method
        List<OrderProductsRequestDTO> orderProductsList = orderProductsService.findAllOrdersProductByOrder(orderUUID);

        // Assertions
        assertEquals(2, orderProductsList.size());
    }
}
