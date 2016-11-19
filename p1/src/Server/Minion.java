package Server;

import Client.GameClientInterface;

import java.util.Map;

public class Minion {
    public int x;
    public int y;
    public int minionID;
    public Map<GameClientInterface, Integer> userScores;
}
