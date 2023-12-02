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

    /**
     * Test for getting an order by UUID, checking if it returns the correct OrderDTO.
     */
    @Test
    void getOrderUuid_ShouldReturnOrderDTO() {
        // Mocking data
        UUID orderUUID = UUID.randomUUID();
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderUuid(orderUUID);
        when(orderRepositoryMock.findById(orderUUID)).thenReturn(Optional.of(orderModel));
        when(orderMapperMock.createOrderDTO(orderModel)).thenReturn(new OrderRequestDTO());

        // Calling the service method
        OrderRequestDTO orderDTO = orderService.getOrderUuid(orderUUID);

        // Assertions
        assertNotNull(orderDTO);
    }

    /**
     * Test for saving an order, checking if it successfully saves the order and returns an OrderDTO.
     */
    @Test
    void saveOrder_ShouldSaveOrderAndReturnOrderDTO() {
        // Mocking data
        when(paymentRepositoryMock.findByType(any())).thenReturn(Optional.of(new PaymentModel()));
        when(statusRepositoryMock.findByType(any())).thenReturn(Optional.of(new StatusModel()));
        when(deliveryRepositoryMock.findByType(any())).thenReturn(Optional.of(new DeliveryModel()));
        ShopUserModel shopUserModel = new ShopUserModel();
        when(shopUserRepositoryMock.findByMail(any())).thenReturn(Optional.of(shopUserModel));
        when(orderMapperMock.createOrderDTO(any())).thenReturn(new OrderRequestDTO());
        UserAddressModel userAddressModel = new UserAddressModel();
        when(userAddressRepositoryMock.findByApartamentAndHomeAndStreet(any(), any(), any()))
                .thenReturn(Optional.of(userAddressModel));

        // Creating an OrderDTO
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

        // Calling the service method
        OrderRequestDTO savedOrderDTO = orderService.saveOrder(orderDTO);

        // Assertions
        assertNotNull(savedOrderDTO);
        verify(orderRepositoryMock).save(any(OrderModel.class));
    }

    /**
     * Test for updating an order status, checking if it successfully updates the order status.
     */
    @Test
    void updateOrder_ShouldUpdateOrderStatus() {
        // Mocking data
        UUID orderUUID = UUID.randomUUID();
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderUuid(orderUUID);
        when(orderRepositoryMock.findById(orderUUID)).thenReturn(Optional.of(orderModel));
        when(statusRepositoryMock.findByType(any())).thenReturn(Optional.of(new StatusModel()));

        // Calling the service method
        orderService.updateOrder(orderUUID, "SHIPPED");

        // Assertions
        assertTrue(orderModel.getPay_status());
        verify(orderRepositoryMock).save(orderModel);
    }

    /**
     * Test for finding a status, checking if it returns the correct StatusModel when the status exists.
     */
    @Test
    void findStatus_ShouldReturnStatusModel_WhenStatusExists() {
        // Mocking data
        String statusType = "SHIPPED";
        when(statusRepositoryMock.findByType(statusType)).thenReturn(Optional.of(new StatusModel()));

        // Calling the service method
        StatusModel statusModel = orderService.findStatus(statusType);

        // Assertions
        assertNotNull(statusModel);
    }

    /**
     * Test for finding a status, checking if it throws a NoExistData exception when the status does not exist.
     */
    @Test
    void findStatus_ShouldThrowException_WhenStatusDoesNotExist() {
        // Mocking data
        String nonExistingStatusType = "NON_EXISTENT_STATUS";
        when(statusRepositoryMock.findByType(nonExistingStatusType)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () -> orderService.findStatus(nonExistingStatusType));

        assertEquals("This status don't exist", exception.getMessage());
    }

    /**
     * Test for finding a delivery, checking if it returns the correct DeliveryModel when the delivery exists.
     */
    @Test
    void findDelivery_ShouldReturnDeliveryModel_WhenDeliveryExists() {
        // Mocking data
        String deliveryType = "EXPRESS";
        when(deliveryRepositoryMock.findByType(deliveryType)).thenReturn(Optional.of(new DeliveryModel()));

        // Calling the service method
        DeliveryModel deliveryModel = orderService.findDelivery(deliveryType);

        // Assertions
        assertNotNull(deliveryModel);
    }

    /**
     * Test for finding a delivery, checking if it throws a NoExistData exception when the delivery does not exist.
     */
    @Test
    void findDelivery_ShouldThrowException_WhenDeliveryDoesNotExist() {
        // Mocking data
        String nonExistingDeliveryType = "NON_EXISTENT_DELIVERY";
        when(deliveryRepositoryMock.findByType(nonExistingDeliveryType)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () -> orderService.findDelivery(nonExistingDeliveryType));

        assertEquals("This delivery don't exist", exception.getMessage());
    }

    /**
     * Test for finding a shop user, checking if it returns the correct ShopUserModel when the shop user exists.
     */
    @Test
    void findShopUser_ShouldReturnShopUserModel_WhenShopUserExists() {
        // Mocking data
        String userEmail = "user@example.com";
        when(shopUserRepositoryMock.findByMail(userEmail)).thenReturn(Optional.of(new ShopUserModel()));

        // Calling the service method
        ShopUserModel shopUserModel = orderService.findShopUser(userEmail);

        // Assertions
        assertNotNull(shopUserModel);
    }

    /**
     * Test for finding a shop user, checking if it throws a NoExistData exception when the shop user does not exist.
     */
    @Test
    void findShopUser_ShouldThrowException_WhenShopUserDoesNotExist() {
        // Mocking data
        String nonExistingUserEmail = "nonexistent@example.com";
        when(shopUserRepositoryMock.findByMail(nonExistingUserEmail)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () -> orderService.findShopUser(nonExistingUserEmail));

        assertEquals("This shop user don't exist", exception.getMessage());
    }

    /**
     * Test for finding a user address, checking if it returns the correct UserAddressModel when the user address exists.
     */
    @Test
    void findUserAddress_ShouldReturnUserAddressModel_WhenUserAddressExists() {
        // Mocking data
        String apartament = "123";
        String home = "456";
        String street = "Main St";
        when(userAddressRepositoryMock.findByApartamentAndHomeAndStreet(apartament, home, street))
                .thenReturn(Optional.of(new UserAddressModel()));

        // Calling the service method
        UserAddressModel userAddressModel = orderService.findUserAddress(apartament, home, street);

        // Assertions
        assertNotNull(userAddressModel);
    }

    /**
     * Test for finding a user address, checking if it throws a NoExistData exception when the user address does not exist.
     */
    @Test
    void findUserAddress_ShouldThrowException_WhenUserAddressDoesNotExist() {
        // Mocking data
        String nonExistingApartament = "789";
        String nonExistingHome = "012";
        String nonExistingStreet = "Other St";
        when(userAddressRepositoryMock.findByApartamentAndHomeAndStreet(nonExistingApartament, nonExistingHome, nonExistingStreet))
                .thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () ->
                orderService.findUserAddress(nonExistingApartament, nonExistingHome, nonExistingStreet));

        assertEquals("This user address don't exist", exception.getMessage());
    }

    /**
     * Test for finding a payment, checking if it returns the correct PaymentModel when the payment exists.
     */
    @Test
    void findPayment_ShouldReturnPaymentModel_WhenPaymentExists() {
        // Mocking data
        String paymentType = "Credit Card";
        when(paymentRepositoryMock.findByType(paymentType)).thenReturn(Optional.of(new PaymentModel()));

        // Calling the service method
        PaymentModel paymentModel = orderService.findPayment(paymentType);

        // Assertions
        assertNotNull(paymentModel);
    }

    /**
     * Test for finding a payment, checking if it throws a NoExistData exception when the payment does not exist.
     */
    @Test
    void findPayment_ShouldThrowException_WhenPaymentDoesNotExist() {
        // Mocking data
        String nonExistingPaymentType = "Nonexistent Payment";
        when(paymentRepositoryMock.findByType(nonExistingPaymentType)).thenReturn(Optional.empty());

        // Assertions
        NoExistData exception = assertThrows(NoExistData.class, () ->
                orderService.findPayment(nonExistingPaymentType));

        assertEquals("This payment method don't exist", exception.getMessage());
    }


}
