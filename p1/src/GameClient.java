
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class GameClient implements Serializable {

    private String username;
    private String password;

    private Minion minion;

    private int point;

    private Map<String, Integer> competitor;
    

    public GameClient(String username, String password) {
        this.username = username;
        this.password = password;
        this.minion = null;
        this.point = 0;
        competitor = new HashMap<String, Integer>();
    }

    public static void main(String args[]) throws Exception {
        GameServerInterface gameServer = (GameServerInterface)Naming.lookup("//localhost/GameServer");

        // 2 following line should be implemented with GUI methods
        String username = "abc";
        String password = "def";

        System.out.println(gameServer.login(username, password));

        GameClient gameClient = gameServer.login(username, password);

        System.out.print(gameServer.findPassword(username));

    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPoint(int point) {
    	this.point = point;
    }

    public int[] getMinionCoordinate() {
        return new int[] {this.minion.getX(), this.minion.getY()};
    }

    public void sendChangesToServer() {
        // TODO

        // call method sendChanges which is implemented in GameServer
        // use return value to update this instance
        // deliver XY & points saved in the refreshed gameClient instance to GUI
    }

    
        
    
    public void loadChanges() throws RemoteException {
        // TODO
        // use gameClient to update this instance
    }

    private void sendXYToGUI(int[] xy) {
        // TODO
    }

    private void sendCompititorsToGUI(Map<String, Integer> competitor) {
        // TODO
    }
}
