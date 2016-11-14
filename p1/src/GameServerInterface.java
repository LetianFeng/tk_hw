
import java.rmi.*;


public interface GameServerInterface extends Remote{

    public GameClient login(String username, String password) throws RemoteException;

    public GameClient sendChanges(GameClient gameClient) throws RemoteException;

    public String getMessage() throws RemoteException;
}
