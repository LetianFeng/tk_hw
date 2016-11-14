import java.rmi.*;

public interface GameServerInterface extends Remote{

    public Boolean login(String username, String password) throws RemoteException;

    public GameClient sendChanges(GameClient gameClient) throws RemoteException;

    public String findPassword(String username) throws RemoteException;

}
