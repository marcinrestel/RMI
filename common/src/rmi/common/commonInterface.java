package rmi.common;

import java.rmi.*;

public interface commonInterface extends Remote {
	public boolean messageServer(String message) throws RemoteException;
}