package GUI;

public interface GameGuiInterface {

    // should implement all the GUI relevant action (e.g. Game Window Initialization, Minion Drawing, Score Drawing
    // and Notification) from Client.

    void initialGUI();

    void drawMinion(int x, int y, int minionId);

    void drawScore(String username, int score, int gamerOrder);

    void drawNotification(String notification);
    
    void cleanScreen();
}
