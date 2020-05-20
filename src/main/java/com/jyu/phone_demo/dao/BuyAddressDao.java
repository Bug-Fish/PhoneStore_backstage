package com.jyu.phone_demo.dao;

import com.jyu.phone_demo.entity.BuyerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyAddressDao extends JpaRepository<BuyerAddress,Integer> {
}
