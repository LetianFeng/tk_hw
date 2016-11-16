import java.rmi.*;

public interface GameServerInterface extends Remote{
	
	public boolean register(String username, String password) throws RemoteException;
    public GameClient login(String username, String password) throws RemoteException;
    public String findPassword(String username) throws RemoteException;

    public boolean sendChanges(int x, int y, String username) throws RemoteException;
    public GameClient loadChanges() throws RemoteException;

}
