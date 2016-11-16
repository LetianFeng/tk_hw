package client;

import java.rmi.*;

public interface GameClientInterface extends Remote {
	public void changesHappened(int x, int y) throws RemoteException;
}