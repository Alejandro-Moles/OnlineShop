package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.*;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.mapper.OrderMapper;
import com.javaschool.onlineshop.models.*;
import com.javaschool.onlineshop.repositories.DeliveryRepository;
import com.javaschool.onlineshop.repositories.OrderRepository;
import com.javaschool.onlineshop.repositories.PaymentRepository;
import com.javaschool.onlineshop.repositories.StatusRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import com.javaschool.onlineshop.repositories.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;
    private final StatusRepository statusRepository;
    private final DeliveryRepository deliveryRepository;
    private final ShopUserRepository shopUserRepository;
    private final UserAddressRepository userAddressRepository;

    private OrderRequestDTO createOrderDTO(OrderModel order){
        return orderMapper.createOrderDTO(order);
    }

    private OrderModel createOrderEntity(OrderRequestDTO orderDTO, OrderModel order){
        order.setPayment(findPayment(orderDTO.getPayment()));
        order.setStatus(findStatus(orderDTO.getStatus()));
        order.setDelivery(findDelivery(orderDTO.getDelivery()));
        order.setShopUser(findShopUser(orderDTO.getMail()));
        order.setPay_status(orderDTO.getPayStatus());
        order.setIsDeleted(orderDTO.getIsDeleted());
        order.setUserAddress(findUserAddress(orderDTO.getApartament(), orderDTO.getHome(), orderDTO.getStreet()));
        order.setDate(orderDTO.getOrderDate());
        return order;
    }

    public OrderRequestDTO getOrderUuid(UUID uuid){
        OrderModel order = loadOrder(uuid);
        return createOrderDTO(order);
    }

    @Transactional
    public OrderRequestDTO saveOrder(OrderRequestDTO orderDTO){
        OrderModel order = createOrderEntity(orderDTO, new OrderModel());
        orderRepository.save(order);
        return createOrderDTO(order);
    }

    @Transactional(readOnly = true)
    public PaymentModel findPayment(String type){
        return paymentRepository.findByType(type).orElseThrow(() -> new NoExistData("This payment method don't exist"));
    }

    @Transactional(readOnly = true)
    public StatusModel findStatus(String type){
        return statusRepository.findByType(type).orElseThrow(() -> new NoExistData("This status don't exist"));
    }

    @Transactional(readOnly = true)
    public DeliveryModel findDelivery(String type){
        return deliveryRepository.findByType(type).orElseThrow(() -> new NoExistData("This delivery don't exist"));
    }

    @Transactional(readOnly = true)
    public ShopUserModel findShopUser(String mail){
        return shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("This shop user don't exist"));
    }

    @Transactional(readOnly = true)
    public UserAddressModel findUserAddress(String apartament, String home, String street){
        return userAddressRepository.findByApartamentAndHomeAndStreet(apartament, home, street).orElseThrow(() -> new NoExistData("This user address don't exist"));
    }

    @Transactional(readOnly = true)
    public List<OrderRequestDTO> getAllOrders(){
        return orderRepository.findAll().stream().map(this::createOrderDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderRequestDTO> getAllOrdersForUser(UUID userUuid){
        return orderRepository.findOrderByShopUser(loadShopUser(userUuid)).stream().map(this::createOrderDTO).toList();
    }

    @Transactional(readOnly = true)
    private OrderModel loadOrder(UUID uuid){
        return orderRepository.findById(uuid).orElseThrow(() -> new NoExistData("Product don't exist"));
    }

    @Transactional
    public void updateOrder(UUID uuid, String status){
        OrderModel orderModel = loadOrder(uuid);
        if(Objects.equals(status, "PENDING_PAYMENT")){
            orderModel.setPay_status(false);
        }else {
            orderModel.setPay_status(true);
        }
        orderModel.setStatus(findStatus(status));
        orderRepository.save(orderModel);
    }

    @Transactional(readOnly = true)
    private ShopUserModel loadShopUser(UUID uuid){
        return shopUserRepository.findById(uuid).orElseThrow(() -> new NoExistData("Shop user don't exist"));
    }

    @Transactional(readOnly = true)
    public List<RevenueStatisticDTO> getRevenueStatistic(DateSelectorStatisticDTO dto){
        return orderRepository.getRevenueStatistics(dto.getStartDate(), dto.getEndDate());
    }
}
