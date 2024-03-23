package com.stock.data.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.data.model.up;
import com.stock.data.model.upperUp;

@Repository
public interface UpRepository extends JpaRepository<up, Long> {
	
List<up> findBystrikePrice(long l);
	
}