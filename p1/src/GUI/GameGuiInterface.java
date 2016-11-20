package GUI;

import Client.GameClientInterface;
import Server.Minion;

import java.util.Map;

public interface GameGuiInterface {

    // should implement all the GUI relevant action (e.g. Game Window Initialization, Minion Drawing, Score Drawing
    // and Notification) from Client.

    void openLoginWindow();

    void closeLoginWindow();

    void openGameWindow(Minion minion);

    void closeGameWindow();

    void drawMinion(int x, int y, int minionID);

    void drawScores(Map<GameClientInterface, Integer> userScores);

    void cleanScreen();

    void drawNotification(String notification);
}
