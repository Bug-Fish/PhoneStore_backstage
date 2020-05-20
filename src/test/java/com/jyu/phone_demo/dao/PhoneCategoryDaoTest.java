package com.jyu.phone_demo.dao;

import com.jyu.phone_demo.entity.PhoneCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PhoneCategoryDaoTest {
    @Autowired
    private PhoneCategoryDao dao;
    @Test
    void findAll(){
        List<PhoneCategory> list=dao.findAll();
        System.out.println(list);
    }

    @Test
    void findbytype(){
        PhoneCategory phoneCategory=dao.findByCategoryType(2);
        System.out.println(phoneCategory);
    }




}