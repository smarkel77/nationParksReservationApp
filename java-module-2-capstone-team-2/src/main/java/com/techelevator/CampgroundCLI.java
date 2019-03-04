package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private Menu menu;
	private CampgroundDAO campgroundDAO;
	private ParkDAO parkDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private Search search;

	private static final String PARK_MENU_OPTION_VIEW_PARKS = "View all National Parks";
	private static final String PARK_MENU_OPTION_QUIT = "Quit";
	private static final String[] MAIN_MENU = new String[] { PARK_MENU_OPTION_VIEW_PARKS, PARK_MENU_OPTION_QUIT };

	private static final String COMMAND_MENU_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String COMMAND_MENU_SEARCH_RESERVATIONS = "Search for Reservations";
	private static final String COMMAND_MENU_RETURN_SCREEN = "Return to Previous Screen";
	private static final String COMMAND_MENU_30_DAY_RESERVATION = "See the next 30 days of reservations";
	private static final String[] COMMAND_MENU_OPTIONS = new String[] { COMMAND_MENU_VIEW_CAMPGROUNDS,
			COMMAND_MENU_SEARCH_RESERVATIONS, 
			COMMAND_MENU_30_DAY_RESERVATION, 
			COMMAND_MENU_RETURN_SCREEN };

	private static final String SEARCH_SIMPLE_SEARCH = "Simple Reservation Search";
	private static final String SEARCH_ADVANCED_SEARCH = "Advanced Reservation Search";
	private static final String RETURN_PREVIOUS_SCREEN_OPTION = "Return to Previous Screen";

	private static final String[] RESERVATION_MENU = new String[] { SEARCH_SIMPLE_SEARCH,
																	SEARCH_ADVANCED_SEARCH,
																	RETURN_PREVIOUS_SCREEN_OPTION };

	public static void main(String[] args) {
		CampgroundCLI application = new CampgroundCLI();
		application.run();
	}

	public CampgroundCLI() {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		menu = new Menu(System.in, System.out);
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		search = new Search();
	}

	public void run() {

		while (true) {
			printHeading("Main Menu");
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU);
			if (choice.equals(PARK_MENU_OPTION_VIEW_PARKS)) {
				System.out.println();
				mainParkMenu();
			} else if (choice.equals(PARK_MENU_OPTION_QUIT)) {
				System.exit(0);
			}
		}
	}

	private void mainParkMenu() {
		Park selectedPark;
		System.out.println("View Park Interface");
		System.out.println("Select a Park for Further Details");
		List<Park> allParks = parkDAO.getAllParks();
		selectedPark = (Park) menu.getChoiceFromOptions(allParks.toArray());
		selectedPark.displayParkInfo();
		handleCampgroundMainMenu(selectedPark);

	}

	private void handleCampgroundMainMenu(Park park) {
		while (true) {
			Park selectedPark = park;
			Campground selectedCampground;
			printHeading("Select an Option");

			String choice = (String) menu.getChoiceFromOptions(COMMAND_MENU_OPTIONS);
			if (choice.equals(COMMAND_MENU_VIEW_CAMPGROUNDS)) {
				List<Campground> allCampgroundInPark = campgroundDAO.getAllCampgroundInPark(park.getParkId());
				for (Campground c : allCampgroundInPark) {
					System.out.println();
					System.out.println(c.toString());
				}
			} else if (choice.equals(COMMAND_MENU_SEARCH_RESERVATIONS)) {
				String choice2 = (String) menu.getChoiceFromOptions(RESERVATION_MENU);
				if (choice2.equals(SEARCH_SIMPLE_SEARCH)) {
					List<Site> openSites = handleReservation(selectedPark);
					makeReservation(openSites);
				} else if(choice2.equals(SEARCH_ADVANCED_SEARCH)) {
					List<Site> openSite2 = handleAdvanceSearch(selectedPark);
					makeReservation(openSite2);
				}
			}else if (choice.equals(COMMAND_MENU_30_DAY_RESERVATION)) {
				List<Reservation> listOfReservations = reservationDAO.getNext30DaysOfReservationsForPark(selectedPark);
				for (Reservation r : listOfReservations) {
					System.out.println(r.toString());
				}
			} else if (choice.equals(COMMAND_MENU_RETURN_SCREEN)) {
				System.out.println();
				mainParkMenu();
			}
		}
	}
	
	private List<Site> handleAdvanceSearch(Park park) {
		Park selectedPark = park;
		Campground selectedCampground;
		List<Site> listOfSites = new ArrayList<Site>();
		return listOfSites;
		
	}

	private void makeReservation(List<Site> listOfSites) {
		Reservation newRes = new Reservation();
		printHeading("Available Sites. Select one to Reserve");
		printHeading("Site ID \t Max Occupancy Accessible Max RV Length Has Utilities");
		Site selectedSite = (Site) menu.getChoiceFromOptions(listOfSites.toArray());
		String reservationName = getUserInput("What is the name for the reservation?");
		newRes.setReservationName(reservationName);
		newRes.setCreateDate(LocalDate.now());
		newRes.setFromDate(search.getArrivalDate());
		newRes.setToDate(search.getDepartureDate());
		newRes.setSiteId(selectedSite.getSiteId());
		reservationDAO.createReservation(newRes);
		System.out.println("Confirmation id = " + newRes.getReservationID());
	}

	private void handleViewSites(Campground campground) {
		List<Site> listOfSites = siteDAO.getsAllSitesFromCampground(campground);
		for (Site site : listOfSites) {
			site.toString();
		}
	}

	private int getCostOfStay(Search search, Campground campground) {
		Period daysBetween = Period.between(search.getArrivalDate(), search.getDepartureDate());
		int days = daysBetween.getDays();
		// BigDecimal totalCost = campground.getDailyFee().multiply(new
		// BigDecimal(days));
		int totalCost = campground.getDailyFee().intValue() * days;
		return totalCost;
	}

	private List<Site> handleReservation(Park park) {
		Park selectedPark = park;
		Campground selectedCampground;
		List<Site> listOfSites = new ArrayList<Site>();
		printHeading("Select a Campground");
		printHeading("  Name    \t Open\tClose\tDaily Fee");
		List<Campground> allCampgroundInPark = campgroundDAO.getAllCampgroundInPark(park.getParkId());
		selectedCampground = (Campground) menu.getChoiceFromOptions(allCampgroundInPark.toArray());
		search.setCampground(selectedCampground);
		String arrivalDate = getUserInput("What is the arrival date? YYYY/MM/DD");
		try {
			LocalDate arrival = LocalDate.parse(arrivalDate, formatter);
			search.setArrivalDate(arrival);
		} catch (Exception e) {
			System.out.println("Invaid date: Start over");
			handleCampgroundMainMenu(selectedPark);
		}
		String departDate = getUserInput("What is the departure date? YYYY/MM/DD");
		try {
			LocalDate depart = LocalDate.parse(departDate, formatter);
			search.setDepartureDate(depart);
		} catch (Exception e) {
			System.out.println("Invaid date: Start over");
			handleCampgroundMainMenu(selectedPark);
		}
		if (checkDates(search)) {
			if (selectedCampground.getOpenFromMm() <= search.getArrivalDate().getMonthValue()
					&& search.getArrivalDate().getMonthValue() <= selectedCampground.getOpenToMM()
					&& search.getDepartureDate().getMonthValue() >= selectedCampground.getOpenFromMm()
					&& selectedCampground.getOpenToMM() >= search.getDepartureDate().getMonthValue()) {
				listOfSites = siteDAO.getOpenSitesFromCampgroundWithinSearchDates(selectedCampground, search);
				if (listOfSites.size() == 0) {
					System.out.println("No Available sites for those dates. Please choose different dates");
					handleCampgroundMainMenu(selectedPark);
				}
				System.out.println();
				System.out.println("Total Cost: $" + getCostOfStay(search, selectedCampground));
				return listOfSites;
			} else {
				System.out.println();
				System.out.println("Park is closed during those dates. Please choose different dates.");
				handleCampgroundMainMenu(selectedPark);
			}

			return listOfSites;
		} else {
			System.out.println();
			System.out.println("Bad dates. Please choose new dates");
			mainParkMenu();
		}
		return listOfSites;
	}

	@SuppressWarnings("resource")
	private String getUserInput(String prompt) {
		System.out.print(prompt + " >>> ");
		return new Scanner(System.in).nextLine();
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	private boolean checkDates(Search search) {
		return (search.getArrivalDate().isBefore(search.getDepartureDate())
				&& search.getArrivalDate().isAfter(search.getDateOfSearch()));
	}

}
