package com.techelevator;


import java.util.ArrayList;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean isCampgroundAvailable(Reservation reservation, Search search) {
		String sqlIsCampgroundAvailable = "SELECT * FROM reservation " + 
				"WHERE site_id = ? AND (( ? BETWEEN from_date AND to_date) " +
				"OR ( ? BETWEEN from_date AND to_date));";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlIsCampgroundAvailable, reservation.getSiteId(), search.getArrivalDate(), search.getDepartureDate());
		if(results.next()) {
			return false;
		}
		return true;
	}
	
	@Override
	public Reservation createReservation (Reservation newReservation) {
		String sqlCreateReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		newReservation.setReservationID(getNextReservationtId());
		jdbcTemplate.update(sqlCreateReservation, newReservation.getReservationID(), newReservation.getSiteId(),
				newReservation.getReservationName(), newReservation.getFromDate(), newReservation.getToDate(),
				newReservation.getCreateDate());
		return newReservation;
	}
	
	@Override
	public Reservation getReservationById(long reservationId) {
		Reservation reservation = null;
		String sqlGetReservationById = "SELECT * FROM reservation WHERE ? = reservation_id;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationById, reservationId);
		if (results.next()) {
			reservation = mapRowToReservation(results);
		}
		return reservation;
	}

	private Reservation mapRowToReservation(SqlRowSet results) {

		Reservation reservation = new Reservation();
		reservation.setReservationID(results.getLong("reservation_id"));
		reservation.setSiteId(results.getLong("site_id"));
		reservation.setReservationName(results.getString("name"));
		reservation.setFromDate(results.getDate("from_date").toLocalDate());
		reservation.setToDate(results.getDate("to_date").toLocalDate());
		reservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return reservation;
	}

	private long getNextReservationtId() {
		SqlRowSet nextReservationResult = jdbcTemplate
				.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if (nextReservationResult.next()) {
			return nextReservationResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new reservation");
		}
	}

	@Override
	public List<Reservation> getNext30DaysOfReservationsForPark(Park park) {
		List<Reservation> listOfReservations = new ArrayList<Reservation>();
		String sqlNext30DayRes = "SELECT * FROM reservation JOIN site " + 
				"ON reservation.site_id = site.site_id JOIN campground " + 
				"ON campground.campground_id = site.campground_id JOIN park " + 
				"ON park.park_id = campground.park_id " + 
				"WHERE reservation.from_date BETWEEN current_date AND current_date + INTEGER '30' " + 
				"AND park.park_id = ? ORDER BY reservation.from_date;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlNext30DayRes, park.getParkId());
		while(results.next()) {
			Reservation reservation = mapRowToReservation(results);
			listOfReservations.add(reservation);
		}
		return listOfReservations;
	}

	


	
}
