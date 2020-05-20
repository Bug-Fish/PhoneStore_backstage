package com.jyu.phone_demo.dao;

import com.jyu.phone_demo.entity.PhoneCategory;
import com.jyu.phone_demo.entity.PhoneInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PhoneInfoDaoTest {
    @Autowired
    private PhoneInfoDao dao;
    @Test
    void findAll(){
        List<PhoneInfo> list=dao.findAll();
        System.out.println(list);
    }
    @Test
    void findbytype(){
        List<PhoneInfo> phoneInfo=dao.findAllByCategoryType(3);
        System.out.println(phoneInfo);
    }

}