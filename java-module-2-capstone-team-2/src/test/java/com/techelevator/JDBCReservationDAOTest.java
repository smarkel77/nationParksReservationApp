package com.techelevator;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCReservationDAOTest extends DAOIntegrationTest {
	
	private JDBCReservationDAO dao;
	private Search searchIn;
	private Search searchOut;
	private Park testPark;
	private final LocalDate ARRIVE_IN = LocalDate.of(1988, 11, 21);
	private final LocalDate DEPART_IN = LocalDate.of(1988, 12, 01);
	private final LocalDate ARRIVE_OUT = LocalDate.of(2020, 12, 12);
	private final LocalDate DEPART_OUT = LocalDate.of(2020, 12, 15);
	private final String TEST_PARK_NAME = "Hot Spring";
	private final String TEST_PARK_LOCATION = "Cheese";
	private final LocalDate TEST_PARK_EST_DATE = LocalDate.of(2012, 05, 06);
	private final int TEST_PARK_AREA = 10023;
	private final int TEST_PARK_VISITORS = 322;
	private final String TEST_PARK_DESC = "Very quaint";
	private static final int TEST_PARK_ID = 99999999;
	
	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new JDBCReservationDAO(getDataSource());
		testPark = new Park();
		searchIn = new Search();
		searchOut = new Search();
		searchIn.setArrivalDate(ARRIVE_IN);
		searchIn.setDepartureDate(DEPART_IN);
		searchOut.setArrivalDate(ARRIVE_OUT);
		searchOut.setDepartureDate(DEPART_OUT);
		
		
	}
	
//	@Test
//	public void get_next_30_days_of_res_for_park() {
//		LocalDate fd = LocalDate.of(1988, 11, 20);
//		LocalDate td = LocalDate.of(1988, 12, 02);
//		LocalDate create = LocalDate.now();
//		Reservation newReservation = getReservation(, "Happy Family", fd, td, create);
//		dao.createReservation(newReservation);
//	}
	@Test
	public void checking_if_campground_is_not_available() {
		LocalDate fd = LocalDate.of(1988, 11, 20);
		LocalDate td = LocalDate.of(1988, 12, 02);
		LocalDate create = LocalDate.now();
		Reservation newReservation = getReservation(1, "Happy Family", fd, td, create);
		dao.createReservation(newReservation);
		Assert.assertFalse(dao.isCampgroundAvailable(newReservation, searchIn));
	}
	
	@Test
	public void checking_if_campground_is_available() {
		LocalDate fd = LocalDate.of(1988, 11, 20);
		LocalDate td = LocalDate.of(1988, 12, 02);
		LocalDate create = LocalDate.now();
		Reservation newReservation = getReservation(1, "Happy Family", fd, td, create);
		dao.createReservation(newReservation);
		Assert.assertTrue(dao.isCampgroundAvailable(newReservation, searchOut));
	}
	
	@Test
	public void get_next_occuring_reservation() {
		LocalDate fd = LocalDate.now();
		LocalDate td = fd.plusDays(1);
		LocalDate create = LocalDate.now();
		Reservation newReservation = getReservation(1, "Happy Family", fd, td, create);
		dao.createReservation(newReservation);
	}
	
	@Test
	public void create_new_reservation() {
		LocalDate fd = LocalDate.of(1988, 11, 20);
		LocalDate td = LocalDate.of(1988, 12, 02);
		LocalDate create = LocalDate.now();
		Reservation newReservation = getReservation(1, "Happy Family", fd, td, create);
		dao.createReservation(newReservation);
		Reservation savedReservation = dao.getReservationById(newReservation.getReservationID());
		Assert.assertNotEquals(null, newReservation.getReservationID());
		assertReservationsAreEqual(newReservation, savedReservation);
	}
	
	private Reservation getReservation(long siteId, String reservationName, LocalDate fromDate, 
			LocalDate toDate, LocalDate createDate) {
		Reservation theReservation = new Reservation();
		theReservation.setSiteId(siteId);
		theReservation.setReservationName(reservationName);
		theReservation.setFromDate(fromDate);
		theReservation.setToDate(toDate);
		theReservation.setCreateDate(createDate);
		return theReservation;
	}
	private void assertReservationsAreEqual (Reservation expected, Reservation actual) {
		Assert.assertEquals(expected.getReservationID(), actual.getReservationID());
		Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
		Assert.assertEquals(expected.getReservationName(), actual.getReservationName());
		Assert.assertEquals(expected.getFromDate(), actual.getFromDate());
		Assert.assertEquals(expected.getToDate(), actual.getToDate());
		Assert.assertEquals(expected.getCreateDate(), actual.getCreateDate());
	}

}

