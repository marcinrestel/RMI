package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.common.*;

public class Servant
  extends UnicastRemoteObject 
  	implements commonInterface {
	
	public Servant() throws RemoteException {
		
	}
	
	public boolean messageServer(String message) {
		System.out.println(message);
		return true;
	}
}

