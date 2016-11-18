package Client;

import java.net.MalformedURLException;
import java.rmi.*;

import Server.GameServerInterface;

public interface GameClientInterface extends Remote {

	void minionChanged(int x, int y) throws RemoteException, MalformedURLException, NotBoundException;

	String getUsername() throws RemoteException;

}