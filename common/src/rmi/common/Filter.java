package rmi.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Filter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date minDate;
	private Date maxDate;
	private boolean shouldBeAvailable;
	private String movieName;

	public Filter() {

	}

	public Filter(Date minDate, Date maxDate, boolean shouldBeAvailable, String movieName) {
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.shouldBeAvailable = shouldBeAvailable;
		this.movieName = movieName;
	}

	public String getMovieName() {
		return movieName;
	}

	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public boolean setMinDate(String date, SimpleDateFormat dt) {
		try {
			this.minDate = dt.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean setMaxDate(String date, SimpleDateFormat dt) {
		try {
			this.maxDate = dt.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean isDateValid(Date value) {
		if (minDate != null && maxDate == null) {
			return !minDate.after(value);
		} else if (minDate == null && maxDate != null) {
			return !maxDate.before(value);
		} else if (minDate != null && maxDate != null) {
			return !minDate.after(value) && !maxDate.before(value);
		}
		return true;
	}

	public boolean isAvailabilityValid() {
		return true;
	}

	public boolean isMovieNameValid(String value) {
		if (movieName != null) {
			return value.toLowerCase().indexOf(movieName.toLowerCase()) >= 0;
		} else {
			return true;
		}
	}

	public void clearAllCurrentlySetFilters() {
		maxDate = null;
		minDate = null;
		movieName = null;
	}
}
