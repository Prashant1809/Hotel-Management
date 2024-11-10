package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.HotelRoom;
import com.example.demo.entity.User;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class userController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HotelRepository hotelRepository;
	
		   
		   @GetMapping("/home")
		    public String homePage(Model model,
		                           @RequestParam(value = "minPrice", required = false) Double minPrice,
		                           @RequestParam(value = "maxPrice", required = false) Double maxPrice) {

		        List<HotelRoom> rooms;
		        String errorMessage = "";
		        if(minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
		            errorMessage = "Please enter min and max price greater than 0 and min price should not be greater than max price.";
		            model.addAttribute("errorMessage", errorMessage);
		        	 return "home";
		        }

		        if (minPrice != null && maxPrice != null) {
		            rooms = ((HotelRepository) hotelRepository).findByPriceBetween(minPrice, maxPrice);
		        } else {
		            rooms = hotelRepository.findAll();
		        }
		        model.addAttribute("rooms", rooms);
		        return "home";
		    }

}
