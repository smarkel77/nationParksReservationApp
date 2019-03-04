package com.techelevator;

import java.time.LocalDate;

public class Reservation {
	
	private long reservationID;
	private long siteId;
	private String reservationName;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createDate;
	
	public Reservation() {
		Site site = new Site();
	}
	
	public String toString() {
		return (reservationID + " \t" + siteId + " \t" + reservationName + " \t" + fromDate + " \t" + toDate + " \t" + createDate);
	}
	
	public long getReservationID() {
		return reservationID;
	}
	public void setReservationID(long reservationID) {
		this.reservationID = reservationID;
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getReservationName() {
		return reservationName;
	}
	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
    
	
}
