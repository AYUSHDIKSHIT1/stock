package com.stock.data.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.data.model.MarketData;

import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<MarketData, Long> {
	
List<MarketData> findBystrikePrice(long l);
	

}