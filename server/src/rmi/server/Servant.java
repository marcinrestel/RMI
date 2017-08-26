package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import rmi.common.*;

public class Servant extends UnicastRemoteObject implements commonInterface {
	private List<Object> cinemaScreenings = Collections.synchronizedList(new ArrayList<Object>());;

	public Servant() throws RemoteException {
		(new Thread(new init(this.cinemaScreenings))).start();
	}

	public List<String> getFilmScreenings() {
		List<String> returnList = Collections.synchronizedList(new ArrayList<String>());
		IntStream.range(0, cinemaScreenings.size()).forEach(idx -> {
			returnList.add("ID: " + idx + " - " + ((MovieScreening) cinemaScreenings.get(idx)).getScreeningInfo());
		});
		return returnList;
	}

	public List<String> getFilmScreenings(Filter filters) {
		List<String> returnList = Collections.synchronizedList(new ArrayList<String>());
		IntStream.range(0, cinemaScreenings.size()).forEach(idx -> {
			if (checkIfScreeningMatchesFilters(((MovieScreening) cinemaScreenings.get(idx)), filters)) {
				returnList.add("ID: " + idx + " - " + ((MovieScreening) cinemaScreenings.get(idx)).getScreeningInfo());
			}
		});
		return returnList;
	}

	public String getRoomArchitecture(int screeningId) {
		try {
			return ((MovieScreening) cinemaScreenings.get(screeningId)).getRoomArchitecture();
		} catch (ArrayIndexOutOfBoundsException e) {
			return "Invalid screening ID \n";
		} catch (IndexOutOfBoundsException e) {
			return "Invalid screening ID \n";
		}
	}
	
	public String reserveSeat(int screeningId, int seatNumber){
		return ((MovieScreening) cinemaScreenings.get(screeningId)).seatReservation(seatNumber);
	}
	
	public String cancelReservation(int screeningId, int seatNumber, int deletionCode){
		return ((MovieScreening) cinemaScreenings.get(screeningId)).reservationCancellation(seatNumber, deletionCode);
	}

	private boolean checkIfScreeningMatchesFilters(MovieScreening screening, Filter filters) {
		if (filters.isMovieNameValid(screening.getMovieName()) && filters.isDateValid(screening.getScreeningDate())) {
			return true;
		} else {
			return false;
		}
	}

	private class init implements Runnable {
		private List<Object> cinemaScreeningsReference;

		public init(List<Object> cinemaScreeningsReference) {
			this.cinemaScreeningsReference = cinemaScreeningsReference;
		}

		public void run() {
			try {
				SimpleDateFormat dt = new SimpleDateFormat("yyyy.MM.dd HH:mm");
				addExampleMovieScreenings(dt);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		private void addExampleMovieScreenings(SimpleDateFormat dt) throws ParseException {
			cinemaScreeningsReference
					.add(new MovieScreening("Forrest Gump", dt.parse("2017.08.27 10:00"), 1, "PG13 3D"));
			cinemaScreeningsReference.add(new MovieScreening("The Emoji Movie", dt.parse("2017.08.28 8:00"), 2, "PG"));
			cinemaScreeningsReference.add(new MovieScreening("Spider-Man", dt.parse("2017.08.28 14:00"), 1, "PG13"));
			cinemaScreeningsReference.add(new MovieScreening("Spider-Man 2", dt.parse("2017.08.28 16:00"), 2, "PG13"));
			cinemaScreeningsReference.add(new MovieScreening("Wolf Warrior", dt.parse("2017.08.29 22:00"), 1, "none"));
		}
	}

}
