package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.mapper.OrderMapper;
import com.javaschool.onlineshop.models.DeliveryModel;
import com.javaschool.onlineshop.models.OrderModel;
import com.javaschool.onlineshop.models.PaymentModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.models.StatusModel;
import com.javaschool.onlineshop.models.UserAddressModel;
import com.javaschool.onlineshop.repositories.DeliveryRepository;
import com.javaschool.onlineshop.repositories.OrderRepository;
import com.javaschool.onlineshop.repositories.PaymentRepository;
import com.javaschool.onlineshop.repositories.StatusRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import com.javaschool.onlineshop.repositories.UserAddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTests {
    private final OrderRepository orderRepositoryMock = Mockito.mock(OrderRepository.class);
    private final OrderMapper orderMapperMock = Mockito.mock(OrderMapper.class);
    private final PaymentRepository paymentRepositoryMock = Mockito.mock(PaymentRepository.class);
    private final StatusRepository statusRepositoryMock = Mockito.mock(StatusRepository.class);
    private final DeliveryRepository deliveryRepositoryMock = Mockito.mock(DeliveryRepository.class);
    private final ShopUserRepository shopUserRepositoryMock = Mockito.mock(ShopUserRepository.class);
    private final UserAddressRepository userAddressRepositoryMock = Mockito.mock(UserAddressRepository.class);
    private final OrderService orderService = new OrderService(
            orderRepositoryMock, orderMapperMock, paymentRepositoryMock,
            statusRepositoryMock, deliveryRepositoryMock, shopUserRepositoryMock, userAddressRepositoryMock
    );

    @Test
    void getOrderUuid_ShouldReturnOrderDTO() {
        UUID orderUUID = UUID.randomUUID();
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderUuid(orderUUID);
        when(orderRepositoryMock.findById(orderUUID)).thenReturn(Optional.of(orderModel));
        when(orderMapperMock.createOrderDTO(orderModel)).thenReturn(new OrderRequestDTO());

        OrderRequestDTO orderDTO = orderService.getOrderUuid(orderUUID);

        assertNotNull(orderDTO);
    }

    @Test
    void saveOrder_ShouldSaveOrderAndReturnOrderDTO() {
        when(paymentRepositoryMock.findByType(any())).thenReturn(Optional.of(new PaymentModel()));
        when(statusRepositoryMock.findByType(any())).thenReturn(Optional.of(new StatusModel()));
        when(deliveryRepositoryMock.findByType(any())).thenReturn(Optional.of(new DeliveryModel()));
        ShopUserModel shopUserModel = new ShopUserModel();
        when(shopUserRepositoryMock.findByMail(any())).thenReturn(Optional.of(shopUserModel));
        when(orderMapperMock.createOrderDTO(any())).thenReturn(new OrderRequestDTO());

        UserAddressModel userAddressModel = new UserAddressModel();
        when(userAddressRepositoryMock.findByApartamentAndHomeAndStreet(any(), any(), any()))
                .thenReturn(Optional.of(userAddressModel));

        OrderRequestDTO orderDTO = new OrderRequestDTO();
        orderDTO.setPayment("Credit Card");
        orderDTO.setStatus("Pending");
        orderDTO.setDelivery("Express");
        orderDTO.setMail("user@example.com");
        orderDTO.setPayStatus(false);
        orderDTO.setIsDeleted(false);
        orderDTO.setApartament("123");
        orderDTO.setHome("456");
        orderDTO.setStreet("Main St");

        OrderRequestDTO savedOrderDTO = orderService.saveOrder(orderDTO);
        System.out.println(savedOrderDTO);

        assertNotNull(savedOrderDTO);
        verify(orderRepositoryMock).save(any(OrderModel.class));
    }

    @Test
    void updateOrder_ShouldUpdateOrderStatus() {
        UUID orderUUID = UUID.randomUUID();
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderUuid(orderUUID);

        when(orderRepositoryMock.findById(orderUUID)).thenReturn(Optional.of(orderModel));
        when(statusRepositoryMock.findByType(any())).thenReturn(Optional.of(new StatusModel()));

        orderService.updateOrder(orderUUID, "SHIPPED");

        assertTrue(orderModel.getPay_status());
        verify(orderRepositoryMock).save(orderModel);
    }

    @Test
    void findStatus_ShouldReturnStatusModel_WhenStatusExists() {
        String statusType = "SHIPPED";
        when(statusRepositoryMock.findByType(statusType)).thenReturn(Optional.of(new StatusModel()));

        StatusModel statusModel = orderService.findStatus(statusType);

        assertNotNull(statusModel);
    }

    @Test
    void findStatus_ShouldThrowException_WhenStatusDoesNotExist() {
        String nonExistingStatusType = "NON_EXISTENT_STATUS";
        when(statusRepositoryMock.findByType(nonExistingStatusType)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () -> orderService.findStatus(nonExistingStatusType));

        assertEquals("This status don't exist", exception.getMessage());
    }

    @Test
    void findDelivery_ShouldReturnDeliveryModel_WhenDeliveryExists() {
        String deliveryType = "EXPRESS";
        when(deliveryRepositoryMock.findByType(deliveryType)).thenReturn(Optional.of(new DeliveryModel()));

        DeliveryModel deliveryModel = orderService.findDelivery(deliveryType);

        assertNotNull(deliveryModel);
    }

    @Test
    void findDelivery_ShouldThrowException_WhenDeliveryDoesNotExist() {
        String nonExistingDeliveryType = "NON_EXISTENT_DELIVERY";
        when(deliveryRepositoryMock.findByType(nonExistingDeliveryType)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () -> orderService.findDelivery(nonExistingDeliveryType));

        assertEquals("This delivery don't exist", exception.getMessage());
    }

    @Test
    void findShopUser_ShouldReturnShopUserModel_WhenShopUserExists() {
        String userEmail = "user@example.com";
        when(shopUserRepositoryMock.findByMail(userEmail)).thenReturn(Optional.of(new ShopUserModel()));

        ShopUserModel shopUserModel = orderService.findShopUser(userEmail);

        assertNotNull(shopUserModel);
    }

    @Test
    void findShopUser_ShouldThrowException_WhenShopUserDoesNotExist() {
        String nonExistingUserEmail = "nonexistent@example.com";
        when(shopUserRepositoryMock.findByMail(nonExistingUserEmail)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () -> orderService.findShopUser(nonExistingUserEmail));

        assertEquals("This shop user don't exist", exception.getMessage());
    }

    @Test
    void findUserAddress_ShouldReturnUserAddressModel_WhenUserAddressExists() {
        String apartament = "123";
        String home = "456";
        String street = "Main St";
        when(userAddressRepositoryMock.findByApartamentAndHomeAndStreet(apartament, home, street))
                .thenReturn(Optional.of(new UserAddressModel()));

        UserAddressModel userAddressModel = orderService.findUserAddress(apartament, home, street);

        assertNotNull(userAddressModel);
    }

    @Test
    void findUserAddress_ShouldThrowException_WhenUserAddressDoesNotExist() {
        String nonExistingApartament = "789";
        String nonExistingHome = "012";
        String nonExistingStreet = "Other St";
        when(userAddressRepositoryMock.findByApartamentAndHomeAndStreet(nonExistingApartament, nonExistingHome, nonExistingStreet))
                .thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () ->
                orderService.findUserAddress(nonExistingApartament, nonExistingHome, nonExistingStreet));

        assertEquals("This user address don't exist", exception.getMessage());
    }

    @Test
    void findPayment_ShouldReturnPaymentModel_WhenPaymentExists() {
        String paymentType = "Credit Card";
        when(paymentRepositoryMock.findByType(paymentType)).thenReturn(Optional.of(new PaymentModel()));

        PaymentModel paymentModel = orderService.findPayment(paymentType);

        assertNotNull(paymentModel);
    }

    @Test
    void findPayment_ShouldThrowException_WhenPaymentDoesNotExist() {
        String nonExistingPaymentType = "Nonexistent Payment";
        when(paymentRepositoryMock.findByType(nonExistingPaymentType)).thenReturn(Optional.empty());

        NoExistData exception = assertThrows(NoExistData.class, () ->
                orderService.findPayment(nonExistingPaymentType));

        assertEquals("This payment method don't exist", exception.getMessage());
    }

}
