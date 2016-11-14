import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {

    private Minion minion;

    private Map<String, String> userPasswords = new HashMap<>();

    private Map<String, Integer> userPoints = new HashMap<>();

    private List<GameClient> userInstances;

    public GameServer() throws RemoteException {
        super(0);
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

    /*
    will be remotely called by client
     */
    @Override
    public Boolean login(String username, String password) throws RemoteException {
        // TODO
        if (userPasswords.containsKey(username)) {
            System.out.println("user name is already exist!");
            return false;
        }
        else {
            System.out.println("user " + username + " initialized.");
            userPasswords.put(username, password);
            userPoints.put(username, 0);
            GameClient gameClient = new GameClient(username, password);
            return true;
        }

        // if gameClient isn't in map userP
        //      add it in the map
        //      initialize client instance

        // if gameClient has right pass
        //      find client instance in map
        //      update minion & points in gameClient instance
        //      return it back
        // else
        //      return null
    }

    /*
    will be remotely called by client in method sendChangesToServer
     */
    @Override
    public GameClient sendChanges(GameClient gameClient) throws RemoteException {
        //TODO

        // if x,y in client instance == server
        //      find client in userPoint, point++
        //      randomNewXY();
        //      distributeChangesToClients();

        return null;
    }

    /*
    generate 2 random integer for new minion
     */
    private int[] randomNewXY() {
        // TODO
        return null;
    }

    /*
    should distribute minion & point changes to all clients
    should use callback interface
    need more knowledge about rmi client register @ server
     */
    private void distributeChangesToClients() {
        // for c : userInstances
        //      update c with new XY & point
        //      c.distributeChanges(c)
    }

    public String findPassword(String username) {
        return userPasswords.get(username);
    }
}
