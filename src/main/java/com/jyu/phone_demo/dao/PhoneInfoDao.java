package com.jyu.phone_demo.dao;

import com.jyu.phone_demo.entity.PhoneCategory;
import com.jyu.phone_demo.entity.PhoneInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneInfoDao extends JpaRepository<PhoneInfo,Integer> {
    public List<PhoneInfo> findAllByCategoryType(Integer categoryType);
}
