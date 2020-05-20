package com.jyu.phone_demo.service;

import com.jyu.phone_demo.form.AddressForm;
import com.jyu.phone_demo.vo.AddressVO;

import java.util.List;

public interface AddressService {
    public List<AddressVO> findAll();
    public void saveOrUpdate(AddressForm addressForm);
}
