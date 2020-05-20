package com.jyu.phone_demo.service.impl;

import com.jyu.phone_demo.form.AddressForm;
import com.jyu.phone_demo.service.AddressService;
import com.jyu.phone_demo.vo.AddressVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AddressServiceImplTest {
    @Autowired
    private AddressService addressService;


    @Test
        void findAll() {
        List<AddressVO> list=addressService.findAll();
        int id=0;
    }

    @Test
    void saveOrUpdate() {
        AddressForm addressForm=new AddressForm();
        addressForm.setId(36);
        addressForm.setName("tythgtrh");
        addressForm.setAddressDetail("cjilu");
        addressForm.setAreaCode("12443");
        addressForm.setCity("hunan");
        addressForm.setCounty("dongcheng");
        addressForm.setProvince("nanjing");
        addressForm.setTel("14264145762");
        addressService.saveOrUpdate(addressForm);
    }
}