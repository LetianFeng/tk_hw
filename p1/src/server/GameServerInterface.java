package server;

import java.rmi.*;
import java.util.Map;
import java.util.UUID;

import client.GameClientInterface;
import game.Room;

public interface GameServerInterface extends Remote {

	Room login(GameClientInterface client, String username, UUID roomID) throws RemoteException;
	
	boolean logout(GameClientInterface client, UUID roomID) throws RemoteException;

	//Map<GameClientInterface, Integer> pushScoresToClient() throws RemoteException;

	void checkMinion(UUID roomID, UUID minionID, GameClientInterface client) throws RemoteException;

}
