package Server;

import java.rmi.*;
import java.util.Map;

import Client.GameClientInterface;

public interface GameServerInterface extends Remote {

	Minion login(GameClientInterface client) throws RemoteException;
	
	boolean logout(GameClientInterface client) throws RemoteException;

	//Map<GameClientInterface, Integer> pushScoresToClient() throws RemoteException;

	boolean checkMinion(int minionID, GameClientInterface client) throws RemoteException;
}
