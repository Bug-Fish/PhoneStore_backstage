package com.jyu.phone_demo.controller;

import com.jyu.phone_demo.service.PhoneService;
import com.jyu.phone_demo.util.ResultVOUtil;
import com.jyu.phone_demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;
    @GetMapping("index")
    public ResultVO index(){
        return ResultVOUtil.success(phoneService.findDataVO());

    }
    @GetMapping("/findByCategoryType/{categoryType}")
    public ResultVO findByCategoryType(
            @PathVariable("categoryType") Integer categoryType){
        return  ResultVOUtil.success(phoneService.findPhoneInfoVOByCategoryType(categoryType));
    }
    @GetMapping("/findSpecsByPhoneId/{phoneId}")
    public ResultVO findSpecsByPhoneId(
            @PathVariable("phoneId") Integer phoneId){
        return ResultVOUtil.success(phoneService.findSpecsByPhoneId(phoneId));
    }
}
