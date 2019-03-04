package com.techelevator;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getAllCampgrounds() {
		List<Campground> listOfCampgrounds = new ArrayList<Campground>();
		String sqlGetAllCampgrounds = "SELECT * FROM campground;"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds);
		while(results.next()) {
			Campground campground = mapRowToCampground(results);
			listOfCampgrounds.add(campground);
		}
		return listOfCampgrounds;
	}

	@Override
	public Campground getCampgroundInfo(long campgroundId) {
		Campground campground = new Campground();
		String sqlGetCampgroundFromId = "SELECT * FROM campground WHERE campground_id = ? ;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundFromId, campgroundId);
		if(results.next()) {
			campground = mapRowToCampground(results);
		}
		return campground;
	}

	@Override
	public List<Campground> getAllCampgroundInPark(long parkId) {
		List<Campground> listOfCampgrounds = new ArrayList<Campground>();
		String sqlGetAllCampgroundsInPark = "SELECT * FROM campground " + 
				"JOIN park\n ON park.park_id = campground.park_id " + 
				"WHERE campground.park_id = ? ;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgroundsInPark, parkId);
		while(results.next()) {
			Campground campground = mapRowToCampground(results);
			listOfCampgrounds.add(campground);
		}
		
		return listOfCampgrounds;
	}
	
	@Override
	public Campground createNewCampground(Campground newCampground) {
		String sqlCreateCampground = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, " +
				"open_to_mm, daily_fee) VALUES (?, ?, ?, ?, ?, ?);";
		newCampground.setCampgroundId(getNextCampgroundId());
		jdbcTemplate.update(sqlCreateCampground, newCampground.getCampgroundId(), newCampground.getParkId(), 
				newCampground.getCampgroundName(), newCampground.getOpenFromMm(), newCampground.getOpenToMM(),
				newCampground.getDailyFee());
		return newCampground;
	}
	
	private long getNextCampgroundId() {
		SqlRowSet nextCampgroundResult = jdbcTemplate.queryForRowSet("SELECT nextval('campground_campground_id_seq')");
		if(nextCampgroundResult.next()) {
			return nextCampgroundResult.getLong(1);
		}else {
			throw new RuntimeException("Something went wrong while getting an id for the new campground");
		}
	}
	
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setParkId(results.getLong("park_id"));
		theCampground.setCampgroundName(results.getString("name"));
		String number = results.getString("open_from_mm");
		theCampground.setOpenFromMm(Integer.parseInt(number));
		number = results.getString("open_to_mm");
		theCampground.setOpenToMM(Integer.parseInt(number));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return theCampground;
	}
	
	

	
	
	

}
