package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rmi.common.*;

public class Servant extends UnicastRemoteObject implements commonInterface {
	private List<Object> cinemaScreenings = Collections.synchronizedList(new ArrayList<Object>());;

	public Servant() throws RemoteException {
		(new Thread(new init(this.cinemaScreenings))).start();
	}

	public List<String> getFilmScreenings() {
		List<String> returnList = Collections.synchronizedList(new ArrayList<String>());
		cinemaScreenings.forEach(screening -> returnList.add(((MovieScreening) screening).getScreeningInfo()));
		return returnList;
	}

	public List<String> getFilmScreenings(Object filters) {
		return null;
	}

	private class init implements Runnable {
		private List<Object> cinemaScreeningsReference;

		public init(List<Object> cinemaScreeningsReference) {
			this.cinemaScreeningsReference = cinemaScreeningsReference;
		}

		public void run() {
			try {
				SimpleDateFormat dt = new SimpleDateFormat("yyyyy.mm.dd hh:mm");
				addExampleMovieScreenings(dt);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		private void addExampleMovieScreenings(SimpleDateFormat dt) throws ParseException {
			cinemaScreeningsReference.add(new MovieScreening("Forrest Gump", dt.parse("2017.19.08 10:00"), "PG13 3D"));
			cinemaScreeningsReference.add(new MovieScreening("The Emoji Movie", dt.parse("2017.19.08 8:00"), "PG"));
			cinemaScreeningsReference.add(new MovieScreening("Spider-Man", dt.parse("2017.19.08 14:00"), "PG13"));
			cinemaScreeningsReference.add(new MovieScreening("Wolf Warrior", dt.parse("2017.19.08 22:00"), "none"));
		}
	}
}
