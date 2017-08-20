package rmi.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieScreening {

	private String name;
	private Date screeningDate;
	private String description;
	private int room;

	public MovieScreening(String name, Date screeningDate, int room, String description) {
		this.name = name;
		this.screeningDate = screeningDate;
		this.room = room;
		this.description = description;
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
}
