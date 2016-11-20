package client;

import java.net.MalformedURLException;
import java.rmi.*;
import server.Minion;

public interface GameClientInterface extends Remote {

	void minionChanged(Minion minion) throws RemoteException, MalformedURLException, NotBoundException;

	String getUsername() throws RemoteException;

	void sendNotification(String notification) throws RemoteException;

}