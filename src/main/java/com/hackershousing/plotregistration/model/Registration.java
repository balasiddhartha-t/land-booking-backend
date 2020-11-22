package com.hackershousing.plotregistration.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.hackershousing.plotregistration.model.Customer;

@Document(collection = "slots")
public class Registration {
	@Id
	private String id;

	private Integer slotNumber;
	private boolean filled;

	private Customer customer;
	
	public Registration() {

	}

	public Registration(int slotNumber, boolean filled, Customer customer) {
		this.slotNumber = slotNumber;
		this.filled = filled;
		this.customer = customer;
	}

	public String getId() {
		return id;
	}


	public Integer getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(Integer slotNumber) {
		this.slotNumber = slotNumber;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", slotNumber=" + slotNumber + ", filled=" + filled + "]";
	}
}
