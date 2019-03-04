package com.techelevator;

import java.time.LocalDate;

public class Park {
	
	private long parkId;
	private String parkName;
	private String location;
	private LocalDate establishDate;
	private int area;
	private int vistors;
	private String description;
	
	public Park() {
		Search search = new Search();
	}
	
	
	public String toString() {
		return parkName;
	}
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getVistors() {
		return vistors;
	}
	public void setVistors(int vistors) {
		this.vistors = vistors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void displayParkInfo() {
		System.out.println(getParkName() + " National Park");
		System.out.println("Location: " + getLocation());
		System.out.println("Established: " + getEstablishDate());
		System.out.println("Area: " + getArea());
		System.out.println("Annual Visitors: " + getVistors());
		System.out.println();
		System.out.println(getDescription());
	}
	
	
	
}
