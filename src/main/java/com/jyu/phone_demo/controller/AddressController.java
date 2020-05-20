package com.jyu.phone_demo.controller;

import com.jyu.phone_demo.exception.PhoneException;
import com.jyu.phone_demo.form.AddressForm;
import com.jyu.phone_demo.service.AddressService;
import com.jyu.phone_demo.util.ResultVOUtil;
import com.jyu.phone_demo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("/list")
    public ResultVO list(){
        return ResultVOUtil.success(addressService.findAll());

    }
    @PostMapping("/create")
    public ResultVO create(@Valid @RequestBody AddressForm addressForm,
                           BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("添加地址的参数有误,addressForm={}",addressForm);
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        addressService.saveOrUpdate(addressForm);
        return ResultVOUtil.success(null);
    }
    @PutMapping("/update")
    public ResultVO update(@Valid @RequestBody AddressForm addressForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("修改地址参数错误,addressForm={}",addressForm);
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        addressService.saveOrUpdate(addressForm);
        return ResultVOUtil.success(null);
    }
}
