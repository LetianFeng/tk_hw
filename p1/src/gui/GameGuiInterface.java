package gui;

import game.Room;
import game.Player;
import java.util.Map;
import java.util.UUID;

public interface GameGuiInterface {

    // should implement all the GUI relevant action (e.g. Game Window Initialization, Minion Drawing, Score Drawing
    // and Notification) from Client.

    void openLoginWindow();

    void closeLoginWindow();

    void openGameWindow(Room room);

    void closeGameWindow();

    void drawMinion(float x, float y, UUID minionID);

    void drawScores(Map<String, Player> players);

    void cleanScreen();

    void drawNotification(String notification);
    
    GameFrame getGameFrame();
}
