package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import server.GameServerInterface;

public class GameClient implements Serializable {

    private String username;
    private String password;

    public int x;
    public int y;

    private int point;

    private Map<String, Integer> competitor;
    

    public GameClient(String username, String password) {
        this.username = username;
        this.password = password;
        x = 0;
        y = 0;
        point = 0;
        competitor = new HashMap<String, Integer>();
    }

    public static void main(String args[]) throws Exception {
        GameServerInterface gameServer = (GameServerInterface)Naming.lookup("//localhost/GameServer");

        // 2 following line should be implemented with GUI methods
        String username = "abc";
        String password = "def";

        System.out.println(gameServer.login(username, password));

        GameClient gameClient = gameServer.login(username, password);

        System.out.println("remote password: " + gameServer.findPassword(username));

        for (int i = 0, j= 0; i < 100; i++, j++) {
        	// sleep()
        	
        	// rewrite in local method sendChanges, only if feed then call it
        	if (gameServer.sendChanges(i, j, username)) {

                // rewrite in local method loadChanges(), periodically call it
                gameClient = gameServer.loadChanges();
        	}
            System.out.println(gameClient.x + " " + gameClient.y);
        }
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

    public void sendChanges() {
        // TODO
        // call method sendChanges() which is implemented in GameServer
    	
        // deliver new XY & points saved in the refreshed gameClient instance to GUI
    }
        
    
    public void loadChanges() throws RemoteException {
        // TODO
        // call loadChanges() of server to update attributes of this instance
    }

    private void sendXYToGUI(int[] xy) {
        // TODO
    }

    private void sendCompititorsToGUI(Map<String, Integer> competitor) {
        // TODO
    }
}
