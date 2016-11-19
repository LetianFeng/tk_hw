package GUI;

public interface GameGuiInterface {

    // should implement all the GUI relevant action (e.g. Game Window Initialization, Minion Drawing, Score Drawing
    // and Notification) from Client.

    void openLoginWindow();

    void closeLoginWindow();

    void openGameWindow();

    void closeGameWindow();

    void drawMinion(int x, int y, int minionID);

    void drawScore(String username, int score, int gamerOrder);

    void cleanScreen();

    void drawNotification(String notification);

    boolean getWindowType();
}
