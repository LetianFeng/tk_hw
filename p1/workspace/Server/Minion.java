package Server;

import Client.GameClientInterface;

import java.io.Serializable;
import java.util.Map;

public class Minion implements Serializable {
    public int x;
    public int y;
    public int minionID;
    public Map<GameClientInterface, Integer> userScores;

    public Minion(int x, int y, int minionID, Map userScores) {
        this.x = x;
        this.y = y;
        this.minionID = minionID;
        this.userScores = userScores;
    }
}
