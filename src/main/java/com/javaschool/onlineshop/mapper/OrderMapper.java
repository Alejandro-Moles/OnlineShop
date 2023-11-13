package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.models.OrderModel;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public OrderRequestDTO createOrderDTO(OrderModel order){
        OrderRequestDTO orderDTO = new OrderRequestDTO();
        orderDTO.setUuid(order.getOrderUuid());
        orderDTO.setPayment(order.getPayment().getType());
        orderDTO.setStatus(order.getStatus().getType());
        orderDTO.setDelivery(order.getDelivery().getType());
        orderDTO.setApartament(order.getUserAddress().getApartament());
        orderDTO.setHome(order.getUserAddress().getHome());
        orderDTO.setStreet(order.getUserAddress().getStreet());
        orderDTO.setMail(order.getShopUser().getMail());
        orderDTO.setPayStatus(order.getPay_status());
        orderDTO.setIsDeleted(order.getIsDeleted());
        orderDTO.setOrderDate(order.getDate());

        return orderDTO;
    }
}
