package rmi.common;

import java.rmi.*;
import java.util.List;

public interface commonInterface extends Remote {
	public List<String> getFilmScreenings() throws RemoteException;
	public List<String> getFilmScreenings(Filter filters) throws RemoteException;
	public String getRoomArchitecture(int screeningId) throws RemoteException;
	public String reserveSeat(int screeningId, int seatNumber) throws RemoteException;
}