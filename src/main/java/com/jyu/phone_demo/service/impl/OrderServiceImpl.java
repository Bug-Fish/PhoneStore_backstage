package com.jyu.phone_demo.service.impl;

import com.jyu.phone_demo.dao.OrderMasterDao;
import com.jyu.phone_demo.dao.PhoneInfoDao;
import com.jyu.phone_demo.dao.PhoneSpecsDao;
import com.jyu.phone_demo.dto.OrderDTO;
import com.jyu.phone_demo.entity.OrderMaster;
import com.jyu.phone_demo.entity.PhoneInfo;
import com.jyu.phone_demo.entity.PhoneSpecs;
import com.jyu.phone_demo.enums.PayStatusEnum;
import com.jyu.phone_demo.enums.ResultEnum;
import com.jyu.phone_demo.exception.PhoneException;
import com.jyu.phone_demo.service.OrderService;
import com.jyu.phone_demo.service.PhoneService;
import com.jyu.phone_demo.util.KeyUtil;
import com.jyu.phone_demo.vo.OrderDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private PhoneSpecsDao phoneSpecsDao;
    @Autowired
    private PhoneInfoDao phoneInfoDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private PhoneService phoneService;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        PhoneSpecs phoneSpecs=phoneSpecsDao.findById(orderDTO.getSpecsId()).get();
        if (phoneSpecs==null){
            log.error("创建订单规格为空,orderMaster={}",phoneSpecs);
            throw new PhoneException(ResultEnum.SPECS_NOT_EXIST);
        }
        BeanUtils.copyProperties(phoneSpecs,orderMaster);
        PhoneInfo phoneInfo=phoneInfoDao.findById(phoneSpecs.getPhoneId()).get();
        if (phoneInfo==null){
            log.error("手机不存在,orderMaster={}",phoneInfo);
            throw new PhoneException(ResultEnum.PHONE_NOT_EXIST);
        }
        BeanUtils.copyProperties(phoneInfo,orderMaster);
        //计算总价
        BigDecimal orderAmount=new BigDecimal(0);
        orderAmount=phoneSpecs.getSpecsPrice().divide(new BigDecimal(100))
                .multiply(new BigDecimal(orderDTO.getPhoneQuantity()))
                .add(orderAmount)
                .add(new BigDecimal(10));
        orderMaster.setOrderAmount(orderAmount);

        //orderID
        orderMaster.setOrderId(KeyUtil.createUniqueKey());
        orderDTO.setOrderId(orderMaster.getOrderId());
        //payStates
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());
        orderMasterDao.save(orderMaster);
        phoneService.subStock(orderDTO.getSpecsId(),orderDTO.getPhoneQuantity());

        return orderDTO;



    }

    @Override
    public OrderDetailVO findOrderDetailByOrderId(String orderId) {
        OrderDetailVO orderDetailVO=new OrderDetailVO();
        OrderMaster orderMaster=orderMasterDao.findById(orderId).get();
//        Optional<OrderMaster> optional=orderMasterDao.findById(orderId);
//        if (optional!=null&&optional.isPresent()){
//            orderMaster= optional.get();
//        }
        if(orderMaster == null){
            log.error("【查询订单】订单不存在,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_EXIST);
        }
        BeanUtils.copyProperties(orderMaster,orderDetailVO);
        orderDetailVO.setSpecsPrice(orderMaster.getSpecsPrice().divide(new BigDecimal(100))+".00");
        return orderDetailVO;
    }

    @Override
    public String pay(String orderId) {
        OrderMaster orderMaster=orderMasterDao.findById(orderId).get();
        if (orderMaster==null){
            log.error("支付订单为空,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (orderMaster.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())){
            orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
            orderMasterDao.save(orderMaster);
        }else {
            log.error("订单已支付，不允许重复支付,orderMaster={}",orderMaster);
        }
        return orderId;

    }
}
