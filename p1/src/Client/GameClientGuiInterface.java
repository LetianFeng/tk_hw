package client;

import java.util.UUID;

public interface GameClientGuiInterface {

    boolean login(String username);

    boolean logout();

    void feedMinion(UUID minionID);

}