package com.techelevator;

import java.util.List;

public interface CampgroundDAO {
	
	public List <Campground> getAllCampgrounds();
	//get list of campgrounds
	
	public Campground getCampgroundInfo(long campgroundId);
	//get id, name, open month, close month, and daily fee
	
	public List<Campground> getAllCampgroundInPark (long parkId);
	// get a list of all the campgrounds withn a certain park
	
	public Campground createNewCampground(Campground newCampground);
	//creates a new campground
	

}
