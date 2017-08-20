package rmi.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieScreening {

	private String name;
	private Date screeningDate;
	private String description;
	private int room;
	private RA[][] roomArchitecture;

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
	
	public String getRoomArchitecture(){
		String architecture = "";
		for(RA[] rows : roomArchitecture){
			for (RA place : rows){
				if(place == RA.NO_SEAT){
					architecture += " ";
				}
				else if(place == RA.SCREEN){
					architecture += "-";
				}
				else if(place == RA.EMPTY){
					architecture += "e";
				}
				else if(place == RA.RESERVED){
					architecture += "r";
				}
				else{
					architecture += "";
				}
			}
			architecture += "\n";
		}
		return architecture;
	}

	private void createRoomArchitectureArray() {
		roomArchitecture = new RA[][]{
			  { RA.NO_SEAT, RA.NO_SEAT, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.SCREEN, RA.NO_SEAT, RA.NO_SEAT },
			  { RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT, RA.NO_SEAT },
			  { RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.NO_SEAT, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
			  { RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.NO_SEAT, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
			  { RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.NO_SEAT, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
			  { RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.NO_SEAT, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
			  { RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.NO_SEAT, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY },
			  { RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.NO_SEAT, RA.NO_SEAT, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY, RA.EMPTY }
			};
	}
}
