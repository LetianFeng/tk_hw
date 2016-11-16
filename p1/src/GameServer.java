import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {

    private int x;
    private int y;

    private Map<String, String> userPasswords;

    private Map<String, Integer> userPoints;

    private List<GameClient> userInstances;

    public GameServer() throws RemoteException {
        super();
        x = 0;
        y = 0;
        userPasswords = new HashMap<>();
        userPoints = new HashMap<>();
        userInstances = new LinkedList<GameClient>();
    }

    public static void main(String args[]) throws Exception {
        System.out.println("RMI server started");

        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }

        //Instantiate RmiServer

        GameServer gameServer = new GameServer();

        // Bind this object instance to the name "RmiServer"
        Naming.rebind("//localhost/GameServer", gameServer);
        System.out.println("PeerServer bound in registry");
        
    }


    /**
     *  use 2 parameters to create an account
     *  
     *  @return true, if no same username on server, otherwise false
     */
	@Override
	public boolean register(String username, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * use 2 parameter to log in
	 * 
	 * @return corresponding client instance, if both parameter are correct
	 */
    @Override
    public GameClient login(String username, String password) throws RemoteException {
        // TODO delete functionality of register(u, p)
        if (!userPasswords.containsKey(username)) {
        	userPasswords.put(username, password);
        	GameClient gameClient = new GameClient(username, password);
        	userInstances.add(gameClient);
            System.out.println("new user registered!");
            return gameClient;
        }
        else if (userPasswords.get(username).equals(password)) {
            System.out.println("user " + username + " logged in!");
            userPoints.put(username, 0);
            GameClient gameClient = new GameClient(username, password);
        	userInstances.add(gameClient);
            return gameClient;
        }
        
        return null;
    }
	
    /**
     * 
     * @return password of username
     */
    public String findPassword(String username) {
    	// TODO implement in the right way!!!
    	GameClient gameClient = userInstances.get(0);
        return gameClient.getPassword();
    }


    /**
     * generate 2 random integer for new minion
     */
    private int[] randomNewXY() {
        // TODO
        return null;
    }

    /**
     * will be remotely called by client
     */
    @Override
    public boolean sendChanges(int x, int y, String username) throws RemoteException {
        //TODO
    	if (this.x == x && this.y == y) {
    		// find client in the list, point++
    		GameClient gameClient = userInstances.get(0);
            // randomNewXY();
    		this.x++;
    		this.y++;
    		gameClient.x++;
    		gameClient.y++;
    		
            // distributeChangesToClients();
    		return true;
    	}
    	return false;
    }

    /**
     * after understanding rmi callback, implement this method
     */
    private void distributeChangesToClients() throws RemoteException {
    	// TODO
        // for c : userInstances
        //      update c with new XY & point
        //      c.loadChanges()
    }

    /**
     * find client instance in the list, and then return
     */
	@Override
	public GameClient loadChanges() throws RemoteException {
		// TODO Auto-generated method stub

    	GameClient gameClient = userInstances.get(0);
    	
        return gameClient;
	}
}
