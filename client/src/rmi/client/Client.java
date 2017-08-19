package rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
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
		Scanner odczyt = new Scanner(System.in);
		int whatToDo;
		do {
			printAvailableOptions();
			whatToDo = odczyt.nextInt();
			(new Thread(new actionController(whatToDo))).start();
		} while (true);
	}

	private void printAvailableOptions() {
		System.out.println("[1] send Hello to server");
		System.out.println("[9] Exit");
	}

	private class actionController implements Runnable {
		private int whatToDo;

		public actionController(int whatToDo) {
			this.whatToDo = whatToDo;
		}

		public void run() {
			try {
				switch (whatToDo) {
				case 1:
					sendMessageToServer(remoteObject, "Hello!");
					break;
				case 9:
					System.exit(0);
					break;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		private void sendMessageToServer(commonInterface remoteObject, String message) throws RemoteException {
			if (remoteObject.messageServer(message) == true) {
				System.out.println("Success!");
			}
		}
	}
}