package com.stock.data.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.data.model.downerDown;
import com.stock.data.model.upperUp;

@Repository
public interface UpperUpRepository extends JpaRepository<upperUp, Long> {
	
List<upperUp> findBystrikePrice(long l);
	
}