package com.techelevator;

public class Site {
	
	private long siteId;
	private long campgroundId;
	private long siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRVLength;
	private boolean utilites;
	
	public Site() {
		Campground campgroud = new Campground();
	}
	
	@Override
	public boolean equals(Object anObject) {
		if(!(anObject instanceof Site)) {
			return false;
		}
		Site otherSite = (Site)anObject;
		return otherSite.getSiteId() == (getSiteId());
	}
	
	public String toString() {
		return (siteId + "\t" + maxOccupancy + "\t" + accessible + "\t" + maxRVLength + "\t" + utilites);
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public long getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(long siteNumber) {
		this.siteNumber = siteNumber;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public int getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public boolean isUtilites() {
		return utilites;
	}
	public void setUtilites(boolean utilites) {
		this.utilites = utilites;
	}
	
	

}
