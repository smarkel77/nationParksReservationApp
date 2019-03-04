package com.techelevator;



import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;



public class JDBCParkDAOTest extends DAOIntegrationTest {
	
	private JDBCParkDAO testDAO;
	
	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		testDAO = new JDBCParkDAO(getDataSource());
	}
	
	@Test
	public void get_all_parks() {
		Park newPark = makeTestPark();
		testDAO.createNewPark(newPark);
		List<Park> listOfPark = testDAO.getAllParks();
		assertParksAreEqual(newPark, listOfPark.get(listOfPark.size() - 1));
	}
	
	@Test
	public void get_park_from_id() {
		Park newPark = makeTestPark();
		testDAO.createNewPark(newPark);
		Park savedPark  = testDAO.getParkInfo(newPark.getParkId());
		Assert.assertNotEquals(null, newPark.getParkId());
		assertParksAreEqual(newPark, savedPark);	
	}
	
	private void assertParksAreEqual (Park expected, Park actual) {
		Assert.assertEquals(expected.getParkId(), actual.getParkId());
		Assert.assertEquals(expected.getParkName(), actual.getParkName());
		Assert.assertEquals(expected.getLocation(), actual.getLocation());
		Assert.assertEquals(expected.getEstablishDate(), actual.getEstablishDate());
		Assert.assertEquals(expected.getArea(), actual.getArea());
		Assert.assertEquals(expected.getVistors(), actual.getVistors());
		Assert.assertEquals(expected.getDescription(), actual.getDescription());
	}
}
