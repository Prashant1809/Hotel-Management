package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.HotelRoom;

public interface HotelRepository extends JpaRepository<HotelRoom, Long> {

	List<HotelRoom> findByPriceBetween(Double minPrice, Double maxPrice);

	
	

}
