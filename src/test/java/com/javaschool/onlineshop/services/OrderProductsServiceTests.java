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

    @Test
    void saveOrderProducts_WhenOrderProductsDoesNotExist_ShouldSaveOrderProducts() {
        when(orderProductsMapperMock.createOrderProductsDTO(any())).thenReturn(new OrderProductsRequestDTO());
        when(orderRepositoryMock.findById(any())).thenReturn(Optional.of(new OrderModel()));
        when(productsRepositoryMock.findByTitle(any())).thenReturn(Optional.of(new ProductsModel()));
        when(orderProductsRepositoryMock.existsByOrderAndProduct(any(OrderModel.class), any(ProductsModel.class)))
                .thenReturn(false);

        OrderProductsRequestDTO orderProductsDTO = new OrderProductsRequestDTO();
        orderProductsDTO.setOrderUUID(UUID.randomUUID());
        orderProductsDTO.setProductTitle("Product");
        orderProductsDTO.setQuantity(2);

        OrderProductsRequestDTO savedOrderProductsDTO = orderProductsService.saveOrderProducts(orderProductsDTO);

        assertNotNull(savedOrderProductsDTO);
    }

    @Test
    void saveOrderProducts_WhenOrderProductsExists_ShouldThrowResourceDuplicateException() {
        when(orderRepositoryMock.findById(any())).thenReturn(Optional.of(new OrderModel()));
        when(productsRepositoryMock.findByTitle(any())).thenReturn(Optional.of(new ProductsModel()));
        when(orderProductsRepositoryMock.existsByOrderAndProduct(any(OrderModel.class), any(ProductsModel.class)))
                .thenReturn(true);

        OrderProductsRequestDTO orderProductsDTO = new OrderProductsRequestDTO();
        orderProductsDTO.setOrderUUID(UUID.randomUUID());
        orderProductsDTO.setProductTitle("Existing Product");
        orderProductsDTO.setQuantity(1);

        assertThrows(ResourceDuplicate.class, () -> orderProductsService.saveOrderProducts(orderProductsDTO));
    }

    @Test
    void findAllOrdersProductByOrder_ShouldReturnOrderProductsList() {
        UUID orderUUID = UUID.randomUUID();
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderUuid(orderUUID);

        when(orderRepositoryMock.findById(orderUUID)).thenReturn(Optional.of(orderModel));
        when(orderProductsRepositoryMock.findOrderProductsByOrder(orderModel))
                .thenReturn(List.of(new OrderProductsModel(), new OrderProductsModel()));

        List<OrderProductsRequestDTO> orderProductsList = orderProductsService.findAllOrdersProductByOrder(orderUUID);

        assertEquals(2, orderProductsList.size());
    }
}
