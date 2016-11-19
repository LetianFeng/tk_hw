package Client;

public interface GameClientGuiInterface {

    boolean login(String username);

    boolean logout();

    void feedMinion(String minionID);
}