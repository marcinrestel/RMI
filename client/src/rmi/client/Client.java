package rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import rmi.common.*;

public class Client {
	private Scanner userInput = new Scanner(System.in);
	String username;
	commonInterface remoteObject;

	public static void main(String[] args) {
		new Client("localhost");
	}

	private Client(String hostname) {
		Registry reg;
		try {
			reg = LocateRegistry.getRegistry(hostname);
			remoteObject = (commonInterface) reg.lookup("Server");
			loopForClientAction(remoteObject);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Bad date format!");
			e.printStackTrace();
		}
	}

	private void loopForClientAction(commonInterface remoteObject) throws RemoteException, ParseException {
		Scanner s = new Scanner(System.in);
		int whatToDo;
		do {
			printAvailableOptions();
			whatToDo = s.nextInt();
			switch (whatToDo) {
			case 1:
				getFilmScreenings(s, remoteObject);
				break;
			case 2:
				printRoomArchitecture(remoteObject, s);
				break;
			case 3:
				reserveSeat(remoteObject, s);
				break;
			case 9:
				System.exit(0);
				break;
			}
		} while (true);
	}

	private void printRoomArchitecture(commonInterface remoteObject, Scanner s) throws RemoteException {
		System.out.print("Type the ID number of the movie: ");
		System.out.println(remoteObject.getRoomArchitecture(s.nextInt()));
	}

	private void printAvailableOptions() {
		System.out.println("------------------------------------");
		System.out.println("[1] Get film screenings");
		System.out.println("[2] Check free seats for specific screening");
		System.out.println("[3] Make a seat reservation");
		System.out.println("[9] Exit");
	}

	private void getFilmScreenings(Scanner s, commonInterface remoteObject) throws RemoteException, ParseException {
		int whatToDo;
		System.out.println("------------------------------------");
		System.out.println("[1] Get all film screenings");
		System.out.println("[2] Get filtered film screenings");
		System.out.println("[9] Go back");
		whatToDo = s.nextInt();
		List<String> filmScreenings;
		switch (whatToDo) {
		case 1:
			filmScreenings = remoteObject.getFilmScreenings();
			break;
		case 2:
			filmScreenings = remoteObject.getFilmScreenings(getUserFilters(s));
			break;
		case 9:
			return;
		default:
			System.out.println("Bad entry!");
			return;
		}
		System.out.println("Available movies:");
		printStringList(filmScreenings);
	}

	private void reserveSeat(commonInterface remoteObject, Scanner s) throws RemoteException {
		int screeningId, seatNumber;
		System.out.print("Type movie ID: ");
		screeningId = s.nextInt();
		System.out.print("Type seat number: ");
		seatNumber = s.nextInt();
		System.out.println(remoteObject.reserveSeat(screeningId, seatNumber));
	}

	private Filter getUserFilters(Scanner s) {
		Filter filters = new Filter();
		SimpleDateFormat dt = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		do {
			printFiltersMenu(filters, dt);
		} while (setFilters(filters, s, dt));
		return filters;
	}

	private void printFiltersMenu(Filter filters, SimpleDateFormat dt) {
		System.out.println("------------------------------------");
		System.out.println("Set filters:");
		System.out.println((filters.getMovieName() == null) ? "[1] Search screenings with a phrase of the movie name"
				: "[1] Change the phrase to search. Current phrase: " + filters.getMovieName());
		System.out.println((filters.getMinDate() == null) ? "[2] Define a minimum date of screening"
				: "[2] Change defined minimum date of screening. Current date: " + dt.format(filters.getMinDate()));
		System.out.println((filters.getMaxDate() == null) ? "[3] Define a maximum date of screening"
				: "[3] Change defined maximum date of screening. Current phrase: " + dt.format(filters.getMaxDate()));
		System.out.println("[4] Clear all currently set filters");
		System.out.println("[9] Search with currently set filters");
	}

	private boolean setFilters(Filter filters, Scanner s, SimpleDateFormat dt) {
		switch (s.nextInt()) {
		case 1:
			System.out.print("Type a phrase to search: ");
			filters.setMovieName(s.next());
			return true;
		case 2:
			System.out.print("Type a date (format: \"YYYY.MM.DD HH:MM\"): ");
			String minDate = s.next() + s.nextLine();
			if (!filters.setMinDate(minDate, dt)) {
				System.out.println("Bad date format!");
			}
			return true;
		case 3:
			System.out.print("Type a date (format: \"YYYY.MM.DD HH:MM\"): ");
			String maxDate = s.next() + s.nextLine();
			if (!filters.setMaxDate(maxDate, dt)) {
				System.out.println("Bad date format!");
			}
			return true;
		case 4:
			filters.clearAllCurrentlySetFilters();
			return true;
		case 9:
			return false;
		default:
			System.out.println("Bad entry!");
			return true;
		}
	}

	private boolean printStringList(List<String> l) {
		IntStream.range(0, l.size()).forEach(idx -> System.out.println(idx + 1 + ". " + l.get(idx)));
		System.out.println();
		return true;
	}
}