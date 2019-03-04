package com.techelevator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public abstract class DAOIntegrationTest {

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private static JDBCReservationDAO dao;

	
	//Test park
	private static final String TEST_PARK_NAME = "Hot Springingtownvile";
	private static final String TEST_PARK_LOCATION = "Cheese";
	private static final LocalDate TEST_PARK_EST_DATE = LocalDate.of(1000, 01, 01);
	private static final int TEST_PARK_AREA = 10023;
	private static final int TEST_PARK_VISITORS = 322;
	private static final String TEST_PARK_DESC = "Very quaint";
	private static final long TEST_PARK_ID = 999999999;
	
	//Test campground
	private static final long TEST_CAMPGROUND_ID = 999999999;
	private static final String TEST_CAMPGROUND_NAME = "Happy";
	private static final int TEST_CAMPGROUND_OPEN_FROM = 02;
	private static final int TEST_CAMPGROUND_OPEN_TO = 11;
	private static final BigDecimal TEST_CAMPGROUND_FEE = new BigDecimal (43.00);
	
	//Test site
	private static final long TEST_SITE_ID = 999999999;
	private static final long TEST_SITE_NUMBER = 999999999;
	private static final int TEST_SITE_MAX_OCCUPANCY = 999999999;
	private static final boolean TEST_SITE_ACCESSIBLE = true;
	private static final int TEST_SITE_MAX_RV_LENGTH = 999999999;
	private static final boolean TEST_SITE_UTILITIES = true;
	
	//Test reservation
	private static final long TEST_RESERVATION_ID = 999999999;
	private static final String TEST_RESERVATION_NAME = "Hamburger Helper";
	private static final LocalDate TEST_RESERVATION_FROM_DATE = LocalDate.of(9999, 01, 01);
	private static final LocalDate TEST_RESERVATION_TO_DATE = LocalDate.of(9999, 02, 01);
	private static final LocalDate TEST_RESERVATION_CREATE_DATE = LocalDate.now();
	
	//Test search in
	private static final Park TEST_PARK = new Park();
	private static final Campground TEST_CAMPGROUND = new Campground();
	private static final LocalDate TEST_SEARCH_ARRIVE_IN = LocalDate.of(9999, 01, 15);
	private static final LocalDate TEST_SEARCH_DEPART_IN = LocalDate.of(9999, 01, 20);
	private static final String TEST_SEARCH_NAME_IN = "Monkey Business";
	private static final int TEST_SEARCH_RV_LENGTH_IN = 2;
	private static final int TEST_SEARCH_MAX_OCCUPANCY_IN = 2;
	private static final boolean TEST_SEARCH_UTILITIES_TRUE = true;
	private static final boolean TEST_SEARCH_ACCESSIBLE_TRUE = true;
	
	private static final LocalDate TEST_SEARCH_DATE_OF_SEARCH = LocalDate.now();
	
	//Test search out
	private static final String TEST_SEARCH_NAME_OUT = "Bonkey Misuness";
	private static final int TEST_SEARCH_RV_LENGTH_OUT = 999999999;
	private static final int TEST_SEARCH_MAX_OCCUPANCY_OUT = 999999999;
	private static final boolean TEST_SEARCH_UTILITIES_FALSE = false;
	private static final boolean TEST_SEARCH_ACCESSIBLE_FALSE = false;
	private static final LocalDate TEST_SEARCH_ARRIVE_OUT = LocalDate.of(2020, 12, 12);
	private static final LocalDate TEST_SEARCH_DEPART_OUT = LocalDate.of(2020, 12, 15);
	
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections 
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	/* This method provides access to the DataSource for subclasses so that 
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	protected Park makeTestPark() {
		Park testPark = new Park();
		testPark.setParkId(TEST_PARK_ID);
		testPark.setParkName(TEST_PARK_NAME);
		testPark.setArea(TEST_PARK_AREA);
		testPark.setDescription(TEST_PARK_DESC);
		testPark.setEstablishDate(TEST_PARK_EST_DATE);
		testPark.setLocation(TEST_PARK_LOCATION);
		testPark.setVistors(TEST_PARK_VISITORS);
		return testPark;
	}
	
	protected Campground makeTestCampground() {
		Campground testCampground = new Campground();
		testCampground.setParkId(TEST_PARK_ID);
		testCampground.setCampgroundId(TEST_CAMPGROUND_ID);
		testCampground.setCampgroundName(TEST_CAMPGROUND_NAME);
		testCampground.setDailyFee(TEST_CAMPGROUND_FEE);
		testCampground.setOpenFromMm(TEST_CAMPGROUND_OPEN_FROM);
		testCampground.setOpenToMM(TEST_CAMPGROUND_OPEN_TO);
		return testCampground;
	}
	
	protected Site makeTestSite() {
		Site testSite = new Site();
		testSite.setSiteId(TEST_SITE_ID);
		testSite.setCampgroundId(TEST_CAMPGROUND_ID);
		testSite.setSiteNumber(TEST_SITE_NUMBER);
		testSite.setAccessible(TEST_SITE_ACCESSIBLE);
		testSite.setMaxOccupancy(TEST_SITE_MAX_OCCUPANCY);
		testSite.setMaxRVLength(TEST_SITE_MAX_RV_LENGTH);
		testSite.setUtilites(TEST_SITE_UTILITIES);
		return testSite;
	}
	
	protected Reservation makeTestReservation() {
		Reservation testReservation = new Reservation();
		testReservation.setSiteId(TEST_SITE_ID);
		testReservation.setCreateDate(TEST_RESERVATION_CREATE_DATE);
		testReservation.setFromDate(TEST_RESERVATION_FROM_DATE);
		testReservation.setReservationID(TEST_RESERVATION_ID);
		testReservation.setReservationName(TEST_RESERVATION_NAME);
		testReservation.setToDate(TEST_RESERVATION_TO_DATE);
		return testReservation;
	}
	
	protected Search makeTestSearchIn() {
		Search testSearchIn = new Search();
		testSearchIn.setArrivalDate(TEST_SEARCH_ARRIVE_IN);
		testSearchIn.setDepartureDate(TEST_SEARCH_DEPART_IN);
		testSearchIn.setDateOfSearch(TEST_SEARCH_DATE_OF_SEARCH);
		testSearchIn.setNeedAccessiblity(TEST_SEARCH_ACCESSIBLE_TRUE);
		testSearchIn.setSearchName(TEST_SEARCH_NAME_IN);
		testSearchIn.setNeedUtilities(TEST_SEARCH_UTILITIES_TRUE);
		testSearchIn.setMaxPeople(TEST_SEARCH_MAX_OCCUPANCY_IN);
		testSearchIn.setRVLength(TEST_SEARCH_RV_LENGTH_IN);
		testSearchIn.setPark(TEST_PARK);
		testSearchIn.setCampground(TEST_CAMPGROUND);
		return testSearchIn;
	}
	
	protected Search makeTestSearchOut() {
		Search testSearchIn = new Search();
		testSearchIn.setArrivalDate(TEST_SEARCH_ARRIVE_OUT);
		testSearchIn.setDepartureDate(TEST_SEARCH_DEPART_OUT);
		testSearchIn.setDateOfSearch(TEST_SEARCH_DATE_OF_SEARCH);
		testSearchIn.setNeedAccessiblity(TEST_SEARCH_ACCESSIBLE_FALSE);
		testSearchIn.setSearchName(TEST_SEARCH_NAME_OUT);
		testSearchIn.setNeedUtilities(TEST_SEARCH_UTILITIES_FALSE);
		testSearchIn.setMaxPeople(TEST_SEARCH_MAX_OCCUPANCY_OUT);
		testSearchIn.setRVLength(TEST_SEARCH_RV_LENGTH_OUT);
		testSearchIn.setPark(TEST_PARK);
		testSearchIn.setCampground(TEST_CAMPGROUND);
		return testSearchIn;
	}
	
	
}
