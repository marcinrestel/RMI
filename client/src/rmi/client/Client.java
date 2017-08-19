package rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
		}
	}

	private void loopForClientAction(commonInterface remoteObject) throws RemoteException {
		Scanner s = new Scanner(System.in);
		int whatToDo;
		do {
			printAvailableOptions();
			whatToDo = s.nextInt();
			switch (whatToDo) {
			case 1:
				getFilmScreenings(s, remoteObject);
				break;
			case 9:
				System.exit(0);
				break;
			}
		} while (true);
	}

	private void printAvailableOptions() {
		System.out.println("[1] Get film screenings");
//		System.out.println("[2] Check free seats for specific screening");
//		System.out.println("[3] Make a seat reservation");
		System.out.println("[9] Exit");
	}
	
	private void getFilmScreenings(Scanner s, commonInterface remoteObject) throws RemoteException{
		int whatToDo;
		System.out.println("[1] Get all film screenings");
		System.out.println("[2] Get filtered film screenings");
		whatToDo = s.nextInt();
		switch (whatToDo) {
		case 1:
			List<String> filmScreenings = remoteObject.getFilmScreenings();
			System.out.println("Available movies:");
			printStringList(filmScreenings);
			break;
		case 2:
			System.out.println("not implemented yet!");
			break;
		}
	}
	
	private boolean printStringList(List<String> l){
		IntStream.range(0,l.size()).forEach(idx -> System.out.println(idx + " - " + l.get(idx)));
		System.out.println();
		return true;
	}
}