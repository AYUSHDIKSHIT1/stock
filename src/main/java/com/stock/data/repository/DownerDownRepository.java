package com.stock.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.data.model.MarketData;
import com.stock.data.model.downerDown;

@Repository
public interface DownerDownRepository extends JpaRepository<downerDown, Long> {
	
List<downerDown> findBystrikePrice(long l);
	
}
