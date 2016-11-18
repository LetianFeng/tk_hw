package Client;

import java.net.MalformedURLException;
import java.rmi.*;

public interface GameClientInterface extends Remote {

	void minionChanged(int x, int y) throws RemoteException, MalformedURLException, NotBoundException;

	String getUsername() throws RemoteException;

}