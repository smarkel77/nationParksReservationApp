package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	public Reservation createReservation (Reservation newReservation);
//makes new reservation
	
	public Reservation getReservationById (long reservationId);
//get reservation by reservation_id
	
	public boolean isCampgroundAvailable(Reservation reservation, Search search);
	//checking to see if a reservation is made for a site on certain dates
	
	public List<Reservation> getNext30DaysOfReservationsForPark(Park park);
	//returns a list of a certain number of reservations in order from next occurring to furtherest away

}
