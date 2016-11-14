
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

public class GameClient implements GameClientInterface{

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

        Boolean loginStatus = gameServer.login(username, password);
        System.out.print(loginStatus);

        String abc = gameServer.findPassword(username);
        System.out.print(abc);

        while(true) {

        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
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

    /*
        here will be a client callback method to be declared in GameClientInterface.java
     */
    @Override
    public void distributeChanges(GameClient gameClient) {
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
