package com.jyu.phone_demo.service.impl;

import com.jyu.phone_demo.dao.BuyAddressDao;
import com.jyu.phone_demo.entity.BuyerAddress;
import com.jyu.phone_demo.form.AddressForm;
import com.jyu.phone_demo.service.AddressService;
import com.jyu.phone_demo.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private BuyAddressDao buyAddressDao;
    @Override
    public List<AddressVO> findAll() {

        List<AddressVO> list=buyAddressDao.findAll().stream()
                .map(e->new AddressVO(
                        e.getAddressId(),
                        e.getAreaCode(),
                        e.getBuyerName(),
                        e.getBuyerPhone(),
                        e.getBuyerAddress()
                )).collect(Collectors.toList());
        return list;

    }

    @Override
    public void saveOrUpdate(AddressForm addressForm) {
        BuyerAddress buyerAddress;
        if (addressForm.getId()==null){
            buyerAddress=new BuyerAddress();

        }else {
            buyerAddress=buyAddressDao.findById(addressForm.getId()).get();
        }
        buyerAddress.setBuyerName(addressForm.getName());
        buyerAddress.setBuyerPhone(addressForm.getTel());
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(addressForm.getProvince())
                .append(addressForm.getCity())
                .append(addressForm.getCounty())
                .append(addressForm.getAddressDetail()) ;
        buyerAddress.setBuyerAddress(stringBuffer.toString());
        buyerAddress.setAreaCode(addressForm.getAreaCode());
        buyAddressDao.save(buyerAddress);

    }
}
