package com.jyu.phone_demo.dao;

import com.jyu.phone_demo.entity.PhoneInfo;
import com.jyu.phone_demo.entity.PhoneSpecs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PhoneSpecsDaoTest {
    @Autowired
    private PhoneSpecsDao dao;
    @Test
    void findAll(){
        List<PhoneSpecs> list=dao.findAll();
        System.out.println(list);
    }
    @Test
    void findbytype(){
        List<PhoneSpecs> phoneInfo=dao.findAllByPhoneId(1);
        System.out.println(phoneInfo);
    }
}