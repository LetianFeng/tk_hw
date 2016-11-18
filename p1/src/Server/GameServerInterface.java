package Server;

import java.rmi.*;
import java.util.Map;

import Client.GameClientInterface;

public interface GameServerInterface extends Remote {

	int[] login(GameClientInterface client) throws RemoteException;
	
	boolean logout(GameClientInterface client) throws RemoteException;

	Map<GameClientInterface, Integer> pushScoresToClient() throws RemoteException;

	boolean checkMinion(int x, int y, int minionId, GameClientInterface client) throws RemoteException;
}
