package com.techelevator;

import java.util.List;

public interface ParkDAO {
	
	public List<Park> getAllParks();
// get all the parks in the parks database
	
	public Park getParkInfo(long parkId);
// return info about the park from its park_id
	
	public Park createNewPark(Park newPark);
// creates a new park in database
}
