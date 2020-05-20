package com.jyu.phone_demo.dao;

import com.jyu.phone_demo.entity.BuyerAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BuyAddressDaoTest {
    @Autowired
    private BuyAddressDao dao;
    @Test
    void findAll(){
        List<BuyerAddress> list=dao.findAll();
        for (BuyerAddress buyerAddress : list) {
            System.out.println(buyerAddress);
        }

    }
    @Test
    void save(){
        BuyerAddress buyerAddress=new BuyerAddress();
        buyerAddress.setAreaCode("230933");
        buyerAddress.setBuyerAddress("水库法国酷睿官方");
        buyerAddress.setBuyerName("小红");
        buyerAddress.setBuyerPhone("1312486328");
        dao.save(buyerAddress);

    }
    @Test
    void update(){
        BuyerAddress buyerAddress=dao.findById(35).get();
        buyerAddress.setBuyerName("111教育哥");
        dao.save(buyerAddress);
    }
}