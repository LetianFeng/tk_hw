import java.rmi.*;

public interface GameClientInterface extends Remote{

    public void distributeChanges(GameClient gameClient) throws RemoteException;

}
