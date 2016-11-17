package Server;

import java.rmi.*;

import Client.GameClientInterface;

public interface GameServerInterface extends Remote {

	public int[] login(GameClientInterface client) throws RemoteException;
	
	public boolean logout(GameClientInterface client) throws RemoteException;

	public boolean setMinion(int x, int y) throws RemoteException;

	// public int[] getMinion() throws RemoteException;

}
