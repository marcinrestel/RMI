package rmi.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieScreening {

	private String name;
	private Date screeningDate;
	private String description;
	private int room;
	private RA[][] roomArchitecture;
	private RA[][] seatsArchitecture;

	public MovieScreening(String name, Date screeningDate, int room, String description) {
		this.name = name;
		this.screeningDate = screeningDate;
		this.room = room;
		this.description = description;
		createRoomArchitectureArray();
	}

	public String getScreeningInfo() {
		return getMovieName() + " - Date: " + getHumanScreeningDate() + " - Description: " + getDescription();
	}

	public String getMovieName() {
		return name;
	}

	public Date getScreeningDate() {
		return screeningDate;
	}

	public String getDescription() {
		return description;
	}

	public int getRoom() {
		return room;
	}

	public String getHumanScreeningDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm");
		return sdf.format(getScreeningDate());
	}

	public String getRoomArchitecture() {
		String architecture = "";
		for (RA[] rows : roomArchitecture) {
			for (RA place : rows) {
				if (place == RA.NO_SEAT) {
					architecture += "    ";
				} else if (place == RA.SCREEN) {
					architecture += "====";
				} else {
					architecture += "";
				}
			}
			architecture += "\n";
		}
		int seatIndex = 1;
		for (RA[] rows : seatsArchitecture) {
			for (RA place : rows) {
				if (place == RA.NO_SEAT) {
					architecture += "    ";
				} else if (place == RA.EMPTY || place == RA.RESERVED) {
					if (place == RA.EMPTY) {
						if (seatIndex >= 10) {
							architecture += "(" + seatIndex + ")";
						} else {
							architecture += "( " + seatIndex + ")";
						}
					} else if (place == RA.RESERVED) {
						architecture += "(--)";
					}
					seatIndex++;
				} else {
					architecture += "";
				}
			}
			architecture += "\n";
		}

		architecture += "\nLegend: \"=\" - screen; \"(16)\" -  empty seat; \"(--)\" - reserved seat \n";
		return architecture;
	}

	private void createRoomArchitectureArray() {
		roomArchitecture = new RA[][] {
				{ RA.NO_SEAT, RA.NO_SEAT, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN,
						RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.NO_SEAT, RA.NO_SEAT },
				{ RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT,
						RA.NO_SEAT, RA.NO_SEAT } };
		seatsArchitecture = new RA[][] {
				{ RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.EMPTY, RA.EMPTY,
						RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
				{ RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.EMPTY, RA.EMPTY,
						RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
				{ RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.EMPTY, RA.EMPTY,
						RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
				{ RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.EMPTY, RA.EMPTY,
						RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
				{ RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.EMPTY,
						RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
				{ RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.EMPTY, RA.EMPTY,
						RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
				{ RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY,
						RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY } };
	}

	public String seatReservation(int seatNumber) {
		if (setSeatReserved(seatNumber)) {
			return "Success";
		} else {
			return "There is no seat with this number available (either reserved or not existing).";
		}
	}

	private boolean setSeatReserved(int seatNumber) {
		int seatIndex = 1;
		for (int i = 0, length = seatsArchitecture.length; i < length; i++) {
			for (int j = 0, length2 = seatsArchitecture[i].length; j < length2; j++) {
				if (seatsArchitecture[i][j] == RA.EMPTY || seatsArchitecture[i][j] == RA.RESERVED) {
					if (seatsArchitecture[i][j] == RA.EMPTY) {
						if (seatNumber == seatIndex) {
							seatsArchitecture[i][j] = RA.RESERVED;
							return true;
						}
					} else if (seatsArchitecture[i][j] == RA.RESERVED) {
						return false;
					}
					seatIndex++;
				}
			}
		}
		return false;
	}
}
