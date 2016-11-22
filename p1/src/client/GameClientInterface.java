package client;

import java.net.MalformedURLException;
import java.rmi.*;
import game.Minion;
import game.Player;

public interface GameClientInterface extends Remote {

	void minionChanged(Minion minion, Player player) throws RemoteException, MalformedURLException, NotBoundException;

	//String getUsername() throws RemoteException;

	void sendNotification(String notification) throws RemoteException;

}