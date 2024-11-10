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


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class adminController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	  
	   
	   @PostMapping("/addRoom")
	    public String addRoom(@ModelAttribute("room") HotelRoom room ,Model model) {
	        HotelRoom roomFromDb = hotelRepository.save(room);

	        if (roomFromDb.getRoomId() != null) {
	        	 List<HotelRoom> rooms = hotelRepository.findAll();
	               model.addAttribute("rooms", rooms);
	            return "redirect:/adminPage";
	        }

	        return "error";
	    }	  
	   

	   
	

	   @PostMapping("/deleteRoom")
	   public String deleteRoom(@RequestParam Long roomId, RedirectAttributes redirectAttributes) {
	       if (hotelRepository.existsById(roomId)) {
	           hotelRepository.deleteById(roomId);
	           redirectAttributes.addFlashAttribute("message", "Room deleted successfully.");
	           return "redirect:/adminPage"; 
	       } 
	       else {
	           redirectAttributes.addFlashAttribute("message", "Room not found.");
	           return "error"; 
	       }
	   }
      

	   @GetMapping("/updateRoom/{id}")
	   public String updateProductForm(@PathVariable("id") long id, Model model) {
	       Optional<HotelRoom> optionalRoom = hotelRepository.findById(id);
	       if (optionalRoom.isPresent()) {
	    	   System.out.println("upadtrr");
	           model.addAttribute("room", optionalRoom.get());
	           return "updateSuccess"; 
	       } else {
	          
	           model.addAttribute("message", "Room not found.");
	    	   System.out.println("error");

	           return "error"; 
	       }
	   }

	   @PostMapping("/updateRoom/{id}")
	   public String updateProduct(@ModelAttribute("room") HotelRoom room) {
	       hotelRepository.save(room);
	       return "redirect:/adminPage";
	   }

	


	   
		   
		
		   
		 

}
