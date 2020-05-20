package com.jyu.phone_demo.service.impl;

import com.jyu.phone_demo.dao.PhoneCategoryDao;
import com.jyu.phone_demo.dao.PhoneInfoDao;
import com.jyu.phone_demo.dao.PhoneSpecsDao;
import com.jyu.phone_demo.entity.PhoneCategory;
import com.jyu.phone_demo.entity.PhoneInfo;
import com.jyu.phone_demo.entity.PhoneSpecs;
import com.jyu.phone_demo.enums.ResultEnum;
import com.jyu.phone_demo.exception.PhoneException;
import com.jyu.phone_demo.service.PhoneService;
import com.jyu.phone_demo.util.PhoneUtil;
import com.jyu.phone_demo.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    private PhoneCategoryDao dao;
    @Autowired
    private PhoneInfoDao dao1;
    @Autowired
    private PhoneSpecsDao dao2;
    @Override
    public DataVO findDataVO() {
        DataVO dataVO=new DataVO();
        List<PhoneCategory> phoneCategoryList=dao.findAll();

        List<PhoneCategoryVO>phoneCategoryVOList=phoneCategoryList.stream()
                .map(e->new PhoneCategoryVO(e.getCategoryName(),e.getCategoryType()))
                .collect(Collectors.toList());
        dataVO.setCategories(phoneCategoryVOList);

        List<PhoneInfo> phoneInfoList=dao1.findAllByCategoryType(phoneCategoryList.get(0).getCategoryType());
        List<PhoneInfoVO> phoneInfoVOList=new ArrayList<>();
        for (PhoneInfo phoneInfo : phoneInfoList) {
            PhoneInfoVO phoneInfoVO=new PhoneInfoVO();
            BeanUtils.copyProperties(phoneInfo,phoneInfoVO);
            phoneInfoVO.setTag(PhoneUtil.createTag(phoneInfo.getPhoneTag()));
            phoneInfoVOList.add(phoneInfoVO);
        }
        dataVO.setPhones(phoneInfoVOList);


        return dataVO;
    }

    @Override
    public List<PhoneInfoVO> findPhoneInfoVOByCategoryType(Integer categoryType) {
         List<PhoneInfo> phoneInfoList=dao1.findAllByCategoryType(categoryType);
         List<PhoneInfoVO> phoneInfoVOList=new ArrayList<>();
         for (PhoneInfo phoneInfo : phoneInfoList) {
            PhoneInfoVO phoneInfoVO=new PhoneInfoVO();
            BeanUtils.copyProperties(phoneInfo,phoneInfoVO);
            phoneInfoVO.setTag(PhoneUtil.createTag(phoneInfo.getPhoneTag()));
            phoneInfoVOList.add(phoneInfoVO);
         }
         return phoneInfoVOList;

    }

    @Override
    public SpecsPackageVO findSpecsByPhoneId(Integer phoneId) {
        PhoneInfo phoneInfo=dao1.findById(phoneId).get();
        List<PhoneSpecs> phoneSpecsList=dao2.findAllByPhoneId(phoneId);

        //tree
        List<PhoneSpecsVO> phoneSpecsVOList=new ArrayList<>();
        List<PhoneSpecsCasVO> phoneSpecsCasVOList=new ArrayList<>();
        PhoneSpecsVO phoneSpecsVO;
        PhoneSpecsCasVO phoneSpecsCasVO;
        for (PhoneSpecs phoneSpecs : phoneSpecsList) {
            phoneSpecsVO=new PhoneSpecsVO();
            phoneSpecsCasVO=new PhoneSpecsCasVO();
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsVO);
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsCasVO);
            phoneSpecsVOList.add(phoneSpecsVO);
            phoneSpecsCasVOList.add(phoneSpecsCasVO);
        }
        TreeVO treeVO=new TreeVO();
        treeVO.setV(phoneSpecsVOList);
        List<TreeVO> treeVOList=new ArrayList<>();
        treeVOList.add(treeVO);

        SkuVO skuVO=new SkuVO();
        Integer price=phoneInfo.getPhonePrice().intValue();
        skuVO.setPrice(price+".00");
        skuVO.setStock_num(phoneInfo.getPhoneStock());
        skuVO.setTree(treeVOList);
        skuVO.setList(phoneSpecsCasVOList);

        SpecsPackageVO specsPackageVO=new SpecsPackageVO();
        specsPackageVO.setSku(skuVO);

        Map<String ,String > goods=new HashMap<>();
        goods.put("picture",phoneInfo.getPhoneIcon());
        specsPackageVO.setGoods(goods);
        return specsPackageVO;
    }

    @Override
    public void subStock(Integer specsId,Integer quantity) {
        PhoneSpecs phoneSpecs=dao2.findById(specsId).get();
        PhoneInfo phoneInfo=dao1.findById(phoneSpecs.getPhoneId()).get();
        Integer result=phoneSpecs.getSpecsStock()-quantity;
        if (result<0){
            log.error("[库存报错]");
            throw new PhoneException(ResultEnum.PHONE_STOCK_ERROR);
        }
        phoneSpecs.setSpecsStock(result);
        dao2.save(phoneSpecs);
        result=phoneInfo.getPhoneStock()-quantity;
        if (result<0){
            log.error("[库存报错]");
            throw new PhoneException(ResultEnum.PHONE_STOCK_ERROR);
        }
        phoneInfo.setPhoneStock(result);
        dao1.save(phoneInfo);


    }
}
