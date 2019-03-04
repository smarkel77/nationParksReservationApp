package com.techelevator;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;



public class JDBCCampgroundDAOTest extends DAOIntegrationTest{
	
	private JDBCCampgroundDAO dao;
	private static final int TEST_PARK_ID = 99999999;
	
	@Before
	public void setup() {
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) " +
				"VALUES (?, 'Cheese', 'Cheese', '1877-10-01', 1001, 19993, 'Homey');";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		jdbcTemplate.update(sqlInsertPark, TEST_PARK_ID);
		dao = new JDBCCampgroundDAO(getDataSource());
	}
	
	@Test
	public void get_all_campgrounds() {
		BigDecimal fee = new BigDecimal(43.00);
		Campground newCampground = getCampground(1, "Cheese", 3, 8, fee);
		dao.createNewCampground(newCampground);
		List<Campground> listOfCampgrounds = dao.getAllCampgrounds();
		assertCampgroundsAreEqual(newCampground, listOfCampgrounds.get(listOfCampgrounds.size() - 1));
	}
	
	@Test
	public void get_campground_info_from_campground_id() {
		BigDecimal fee = new BigDecimal(43.00);
		Campground newCampground = getCampground(1, "Cheese", 3, 8, fee);
		dao.createNewCampground(newCampground);
		Campground savedCampground = dao.getCampgroundInfo(newCampground.getCampgroundId());
		Assert.assertNotEquals(null,  newCampground.getCampgroundId());
		assertCampgroundsAreEqual(savedCampground, newCampground);
	}
	
	@Test
	public void get_all_campgrounds_in_park() {
		BigDecimal fee = new BigDecimal(43.00);
		Campground newCampground = getCampground(TEST_PARK_ID, "Cheese", 3, 8, fee);
		dao.createNewCampground(newCampground);
		List<Campground> listOfCampgrounds = dao.getAllCampgroundInPark(TEST_PARK_ID);
		Assert.assertNotNull(listOfCampgrounds);
		Assert.assertEquals(1, listOfCampgrounds.size());
		Campground savedCampground = listOfCampgrounds.get(0);
		assertCampgroundsAreEqual(savedCampground, newCampground);
	}
	
	private Campground getCampground(long parkId, String campgroundName, int openFromMm, 
				int openToMM, BigDecimal dailyFee) {
		Campground theCampground = new Campground();
		theCampground.setParkId(parkId);
		theCampground.setCampgroundName(campgroundName);
		theCampground.setOpenFromMm(openFromMm);
		theCampground.setOpenToMM(openToMM);
		theCampground.setDailyFee(dailyFee);
		return theCampground;
	}
	
	private void assertCampgroundsAreEqual(Campground expected, Campground actual) {
		Assert.assertEquals(expected.getParkId(), actual.getParkId());
		Assert.assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		Assert.assertEquals(expected.getCampgroundName(), actual.getCampgroundName());
		Assert.assertEquals(expected.getOpenFromMm(), actual.getOpenFromMm());
		Assert.assertEquals(expected.getOpenToMM(), actual.getOpenToMM());
		Assert.assertEquals(expected.getDailyFee(), actual.getDailyFee());
	}

}
