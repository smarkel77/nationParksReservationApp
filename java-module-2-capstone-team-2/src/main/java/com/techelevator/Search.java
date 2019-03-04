package com.techelevator;

import java.time.LocalDate;

public class Search {
	private Park park;
	private Campground campground;
	private LocalDate arrivalDate;
	private LocalDate departureDate;
	private LocalDate dateOfSearch = LocalDate.now();
	private String searchName;
	private int RVLength;
	private int maxPeople;
	private boolean needAccessiblity;
	private boolean needUtilities;
	
	public Park getPark() {
		return park;
	}
	public void setPark(Park park) {
		this.park = park;
	}
	public Campground getCampground() {
		return campground;
	}
	public void setCampground(Campground campground) {
		this.campground = campground;
	}
	
	public int getRVLength() {
		return RVLength;
	}
	public void setRVLength(int rVLength) {
		RVLength = rVLength;
	}
	public int getMaxPeople() {
		return maxPeople;
	}
	public void setMaxPeople(int max_people) {
		this.maxPeople = max_people;
	}
	public boolean isNeedAccessiblity() {
		return needAccessiblity;
	}
	public void setNeedAccessiblity(boolean needAccessiblity) {
		this.needAccessiblity = needAccessiblity;
	}
	public boolean isNeedUtilities() {
		return needUtilities;
	}
	public void setNeedUtilities(boolean needUtilities) {
		this.needUtilities = needUtilities;
	}
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public LocalDate getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}
	public LocalDate getDateOfSearch() {
		return dateOfSearch;
	}
	public void setDateOfSearch(LocalDate dateOfSearch) {
		this.dateOfSearch = dateOfSearch;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	
	

}
