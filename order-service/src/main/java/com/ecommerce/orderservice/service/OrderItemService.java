package com.ecommerce.orderservice.service;



import com.ecommerce.orderservice.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItem(Integer Id);
    String save(Integer userId, Integer ProductId, String token);

    String placeOrder(Integer userId, Integer orderId, String token);
}
