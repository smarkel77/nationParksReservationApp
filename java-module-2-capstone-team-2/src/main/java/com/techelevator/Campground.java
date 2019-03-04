package com.techelevator;

import java.math.BigDecimal;


public class Campground {
	
	
	private long campgroundId;
	private long parkId;
	private String campgroundName;
	private int openFromMm;
	private int openToMM;
	private BigDecimal dailyFee;
	
	
	public Campground() {
		Park park = new Park();
	}
	
	
	public String toString() {
		return (campgroundName + " \t" + convertMonthToString(openFromMm) + " \t" + convertMonthToString(openToMM) + " \t$" + dailyFee);
	}
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getCampgroundName() {
		return campgroundName;
	}
	public void setCampgroundName(String campgroundName) {
		this.campgroundName = campgroundName;
	}
	public int getOpenFromMm() {
		return openFromMm;
	}
	public void setOpenFromMm(int openFromMm) {
		this.openFromMm = openFromMm;
	}
	public int getOpenToMM() {
		return openToMM;
	}
	public void setOpenToMM(int openToMM) {
		this.openToMM = openToMM;
	}
	public BigDecimal getDailyFee() {
		dailyFee = dailyFee.setScale(2);
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee.setScale(2);
	}
	private String convertMonthToString(int month) {
		String monthString = null;
		switch (month) {
        case 1:  monthString = "January";
                 break;
        case 2:  monthString = "February";
                 break;
        case 3:  monthString = "March";
                 break;
        case 4:  monthString = "April";
                 break;
        case 5:  monthString = "May";
                 break;
        case 6:  monthString = "June";
                 break;
        case 7:  monthString = "July";
                 break;
        case 8:  monthString = "August";
                 break;
        case 9:  monthString = "September";
                 break;
        case 10: monthString = "October";
                 break;
        case 11: monthString = "November";
                 break;
        case 12: monthString = "December";
                 break;
    }
		return monthString;
		
	}
	
}
