package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Park createNewPark(Park newPark) {
		String sqlCreatePark = "INSERT INTO park (park_id, name, location, establish_date, " +
					"area, visitors, description) VALUES (?, ?, ?, ?, ?, ?, ?);";
		newPark.setParkId(getNextParkId());
		jdbcTemplate.update(sqlCreatePark, newPark.getParkId(), newPark.getParkName(), newPark.getLocation(),
				 newPark.getEstablishDate(), newPark.getArea(), newPark.getVistors(), newPark.getDescription());
		return newPark;
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> listOfParks = new ArrayList<Park>();
		String sqlGetAllParks = "SELECT * FROM park ORDER BY name;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while(results.next()) {
			Park park = mapRowToPark(results);
			listOfParks.add(park);
		}
		return listOfParks;
	}
 
	@Override
	public Park getParkInfo(long parkId) {
		Park thePark = null;
		String sqlGetParkInfo = "SELECT * FROM park WHERE park_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkInfo, parkId);
		if (results.next()) {
			thePark = mapRowToPark(results);
		}
		return thePark;
	}
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setParkName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getInt("area"));
		thePark.setVistors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}
	
	private long getNextParkId() {
		SqlRowSet nextParkResult = jdbcTemplate.queryForRowSet("SELECT nextval('park_park_id_seq')");
		if (nextParkResult.next()) {
			return nextParkResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new park");
		}
	}
}
