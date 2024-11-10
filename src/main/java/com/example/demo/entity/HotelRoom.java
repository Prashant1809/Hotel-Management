package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HotelRoom {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long roomId;
	 	
	 	private boolean availability;
	 	
	 	private Long price;
	 	
	 	private String roomType;

		public HotelRoom(Long roomId, boolean availability, Long price, String roomType) {
			super();
			this.roomId = roomId;
			this.availability = availability;
			this.price = price;
			this.roomType = roomType;
		}

		public HotelRoom() {
			super();
		}

		public Long getRoomId() {
			return roomId;
		}

		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}

		public boolean isAvailability() {
			return availability;
		}

		public void setAvailability(boolean availability) {
			this.availability = availability;
		}

		public Long getPrice() {
			return price;
		}

		public void setPrice(Long price) {
			this.price = price;
		}

		public String getRoomType() {
			return roomType;
		}

		public void setRoomType(String roomType) {
			this.roomType = roomType;
		}	

}
