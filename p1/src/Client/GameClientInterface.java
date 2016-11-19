package Client;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Map;

import Server.GameServerInterface;

public interface GameClientInterface extends Remote {

	void minionChanged(int newID, int newX, int newY, Map<GameClientInterface, Integer> userScores) throws RemoteException, MalformedURLException, NotBoundException;

	String getUsername() throws RemoteException;

}