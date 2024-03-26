package com.model;

import java.util.Scanner;

import com.controller.RentalSystem;

public class Customer {
	
	private static Scanner sc = new Scanner(System.in);
	
	private int customerId;
	private String name;
	private long mobNo;
	private long adhaarNo;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getMobNo() {
		return mobNo;
	}
	public void setMobNo(long mobNo) {
		this.mobNo = mobNo;
	}
	public long getAdhaarNo() {
		return adhaarNo;
	}
	public void setAdhaarNo(long adhaarNo) {
		this.adhaarNo = adhaarNo;
	}

	
	
}
