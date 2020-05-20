package com.jyu.phone_demo.dao;

import com.jyu.phone_demo.entity.OrderMaster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderMasterDaoTest {
    @Autowired
    OrderMasterDao dao;
    @Test
    void findAll(){
        List<OrderMaster> list=dao.findAll();
        System.out.println(list);
    }
    @Test
    void save(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setBuyerAddress("wfwrgvwr");
        orderMaster.setBuyerName("sgvrg");
        orderMaster.setBuyerPhone("ewfre");
        orderMaster.setOrderAmount(new BigDecimal(2343));
        orderMaster.setPayStatus(1);
        orderMaster.setOrderId("11");
        orderMaster.setSpecsPrice(new BigDecimal(2343));
        orderMaster.setPhoneIcon("https://img.yzcdn.cn/vant/logo.png");
        orderMaster.setPhoneId(34);
        orderMaster.setPhoneName("wefr");
        orderMaster.setPhoneQuantity(22);
        orderMaster.setSpecsId(1);
        orderMaster.setSpecsName("efrw");
        dao.save(orderMaster);
    }
    @Test
    void findById(){
        OrderMaster orderMaster=dao.findById("11").get();
        System.out.println(orderMaster);
    }
    @Test
    void pay(){
        OrderMaster orderMaster=dao.findById("11").get();
        orderMaster.setPayStatus(1);
        dao.save(orderMaster);
    }
}