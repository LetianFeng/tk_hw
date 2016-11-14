import java.rmi.*;

/**
 * Created by letian on 14.11.16.
 */
public interface GameClientInterface extends Remote{

    public void distributeChanges(GameClient gameClient) throws RemoteException;

}
