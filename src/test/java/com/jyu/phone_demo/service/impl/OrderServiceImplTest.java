package com.jyu.phone_demo.service.impl;

import com.jyu.phone_demo.dto.OrderDTO;
import com.jyu.phone_demo.service.OrderService;
import com.jyu.phone_demo.vo.OrderDetailVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("咋不见反倒是");
        orderDTO.setBuyerPhone("24526547");
        orderDTO.setBuyerAddress("wetfrwgtergergreg");
        orderDTO.setSpecsId(1);
        orderDTO.setPhoneQuantity(1);
        OrderDTO result=orderService.create(orderDTO);
        System.out.println(result);
    }

    @Test
    void findOrderDetailByOrderId() {
        OrderDetailVO orderDetailVO=orderService.findOrderDetailByOrderId("1589201094555637515");
        int id=0;
    }

    @Test
    void pay() {
        System.out.println(orderService.pay("1589201094555637515"));
    }
}