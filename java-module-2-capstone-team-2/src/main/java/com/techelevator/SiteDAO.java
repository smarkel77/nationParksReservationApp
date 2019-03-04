package com.techelevator;

import java.util.List;

public interface SiteDAO {
	
	public List<Site> getOpenSitesFromCampgroundWithinSearchDates(Campground campground, Search search);
//return list of sites from a campground that are with the search dates
	
	public boolean doesSiteHaveUtilities(Site site);
//return true if site has utilities
	
	public boolean isRVlongEnough (Site site, Search search);
//returns true if an RV will fit in site
	
	public boolean isSiteAccessible (Site site);
//return true if site is handicap accessible
	
	public boolean checksMaxOccupancy (Site site, Search search);
//returns true if site will fix max people
	
	public List<Site> getsAllSitesFromCampground(Campground campground);
	
	public Site createNewSite (Site newSite);
	
	

}
