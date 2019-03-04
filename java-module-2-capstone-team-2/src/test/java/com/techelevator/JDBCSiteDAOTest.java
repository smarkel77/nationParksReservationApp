package com.techelevator;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCSiteDAOTest extends DAOIntegrationTest{
	private JDBCSiteDAO dao;
	
	
	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new JDBCSiteDAO(getDataSource());
	}
	
	@Test
	public void create_new_site() {
		long siteId = 999999999;
		long campgroundId = 999999999;
		long siteNumber = 999999999;
		int maxOccupancy = 999999999;
		boolean accessible = true;
		int maxRVLength = 999999999;
		boolean utilities = true;
		Site newSite = getSite(siteId, campgroundId, siteNumber, maxOccupancy, accessible, maxRVLength, utilities);
		dao.createNewSite(newSite);
		
	}
	
	private Site getSite(long siteId, long campgroundId, long siteNumber, int maxOccupancy, boolean accessible, int maxRVLength, boolean utilities) {
		Site theSite = new Site();
		theSite.setSiteId(siteId);
		theSite.setCampgroundId(campgroundId);
		theSite.setSiteNumber(siteNumber);
		theSite.setMaxOccupancy(maxOccupancy);
		theSite.setAccessible(accessible);
		theSite.setMaxRVLength(maxRVLength);
		theSite.setUtilites(utilities);
		return theSite;
	}
	
	private void assertSitesAreEqual(Site expected, Site actual) {
		Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
		Assert.assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		Assert.assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
		Assert.assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
		Assert.assertEquals(expected.getMaxRVLength(), actual.getMaxRVLength());
		Assert.assertEquals(expected.isAccessible(), actual.isAccessible());
		Assert.assertEquals(expected.isUtilites(), actual.isUtilites());
	}

}
