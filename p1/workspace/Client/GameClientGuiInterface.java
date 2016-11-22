package Client;


public interface GameClientGuiInterface {

    boolean login(String username);

    boolean logout();

    void feedMinion(int minionID);

}