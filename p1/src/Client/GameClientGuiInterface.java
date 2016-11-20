package client;


public interface GameClientGuiInterface {

    boolean login(String username);

    boolean logout();

    void feedMinion(int minionID);

}