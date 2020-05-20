package com.jyu.phone_demo.service;

import com.jyu.phone_demo.dto.OrderDTO;
import com.jyu.phone_demo.vo.OrderDetailVO;

public interface OrderService {
    public OrderDTO create(OrderDTO orderDTO);
    public OrderDetailVO findOrderDetailByOrderId(String orderId);
    public String pay(String orderId);
}
