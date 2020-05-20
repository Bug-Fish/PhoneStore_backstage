package com.jyu.phone_demo.controller;

import com.jyu.phone_demo.dto.OrderDTO;
import com.jyu.phone_demo.exception.PhoneException;
import com.jyu.phone_demo.form.OrderForm;
import com.jyu.phone_demo.service.OrderService;
import com.jyu.phone_demo.service.PhoneService;
import com.jyu.phone_demo.util.ResultVOUtil;
import com.jyu.phone_demo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/create")
    public ResultVO create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("创建订单失败,orderForm={}",orderForm);
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());

        }
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getTel());
        orderDTO.setPhoneQuantity(orderForm.getQuantity());
        orderDTO.setSpecsId(orderForm.getSpecsId());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        OrderDTO result=orderService.create(orderDTO);
        Map<String ,String> map=new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);

    }
    @GetMapping("/detail/{orderId}")
    public ResultVO findOrderDetail(@PathVariable("orderId") String orderId){
        return ResultVOUtil.success(orderService.findOrderDetailByOrderId(orderId));
    }
    @PutMapping("/pay/{orderId}")
    public ResultVO pay(@PathVariable("orderId") String orderId){
        Map<String ,String> map=new HashMap<>();
        map.put("orderId",orderService.pay(orderId));
        return ResultVOUtil.success(map);
    }

}
