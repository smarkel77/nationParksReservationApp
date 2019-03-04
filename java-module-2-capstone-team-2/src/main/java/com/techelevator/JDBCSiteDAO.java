package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	
	public Site createNewSite (Site newSite) {
		String sqlCreateSite = "INSERT INTO site (site_id, campground_id, site_number, " +
				"max_occupancy, accessible, max_rv_length, utilities) VALUES (?, ?, ?, ?, ?, ?, ?);";
		newSite.setSiteId(getNextSitetId());
		jdbcTemplate.update(sqlCreateSite, newSite.getSiteId(), newSite.getCampgroundId(), 
				newSite.getSiteNumber(), newSite.getMaxOccupancy(), newSite.isAccessible(), 
				newSite.getMaxRVLength(), newSite.isUtilites());
		return newSite;		
		}
		
	
	@Override
	public List<Site> getOpenSitesFromCampgroundWithinSearchDates(Campground campground, Search search) {
		List<Site> unavailableSites = new ArrayList<Site>();
		String sqlGetOpenSitesFromCampground = "SELECT DISTINCT * FROM campground JOIN site "
				+ "ON campground.campground_id = site.campground_id JOIN reservation "
				+ "ON reservation.site_id = site.site_id WHERE campground.campground_id = ? AND "
				+ "(from_date BETWEEN ? AND ? ) AND (to_date " + "BETWEEN ? AND ? );";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetOpenSitesFromCampground, campground.getCampgroundId(),
				search.getArrivalDate(), search.getDepartureDate(), search.getArrivalDate(), search.getDepartureDate());
		while (results.next()) {
			Site site = mapRowToSite(results);
			unavailableSites.add(site);
		}

		List<Site> allSites = getsAllSitesFromCampground(campground);
		allSites.removeAll(unavailableSites);
		if(allSites.size() > 5) {
			List<Site> topFive = new ArrayList<Site>();
			for(int i = 0; i < 5; i++) {
				topFive.add(allSites.get(i));
			}
			allSites = topFive;
		}
		return allSites;
	}

	@Override
	public boolean doesSiteHaveUtilities(Site site) {
		String sqlHaveUtilities = "SELECT * FROM site WHERE site_id = ? AND utilities = TRUE;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlHaveUtilities, site.getSiteId());
		return (results.next());
	}

	@Override
	public boolean isRVlongEnough(Site site, Search search) {
		String sqlRVLongEnough = "SELECT * FROM site WHERE site_id = ? AND max_rv_length >= ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlRVLongEnough, site.getSiteId(), search.getRVLength());
		return (results.next());
	}

	@Override
	public boolean isSiteAccessible(Site site) {
		String sqlHaveUtilities = "SELECT * FROM site WHERE site_id = ? AND accessible = TRUE;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlHaveUtilities, site.getSiteId());
		return (results.next());
	}

	@Override
	public boolean checksMaxOccupancy(Site site, Search search) {
		String sqlRVLongEnough = "SELECT * FROM site WHERE site_id = ? AND max_occupancy >= ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlRVLongEnough, site.getSiteId(), search.getMaxPeople());
		return (results.next());
	}

	private Site mapRowToSite(SqlRowSet results) {
		Site site = new Site();
		site.setSiteId(results.getLong("site_id"));
		site.setCampgroundId(results.getLong("campground_id"));
		site.setSiteNumber(results.getLong("site_number"));
		site.setMaxOccupancy(results.getInt("max_occupancy"));
		site.setAccessible(results.getBoolean("accessible"));
		site.setMaxRVLength(results.getInt("max_rv_length"));
		site.setUtilites(results.getBoolean("utilities"));
		return site;
	}
	private long getNextSitetId() {
		SqlRowSet nextSiteResult = jdbcTemplate
				.queryForRowSet("SELECT nextval('site_site_id_seq')");
		if (nextSiteResult.next()) {
			return nextSiteResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new site");
		}
	}

	@Override
	public List<Site> getsAllSitesFromCampground(Campground campground) {
		List<Site> listOfSites = new ArrayList<Site>();
		String sqlAllSitesFromCampground = "SELECT * FROM campground JOIN site "
				+ "ON campground.campground_id = site.campground_id " + "WHERE campground.campground_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllSitesFromCampground, campground.getCampgroundId());
		while (results.next()) {
			Site site = mapRowToSite(results);
			listOfSites.add(site);
		}
		return listOfSites;
	}

}
