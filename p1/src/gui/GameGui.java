package gui;

import client.GameClientGuiInterface;
import client.GameClientInterface;
import game.Minion;
import game.Player;
import game.Room;
import game.Util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GameGui implements GameGuiInterface, Runnable{

	private int minionID;
	private GameClientGuiInterface client;
	private LoginFrame loginFrame;
	private GameFrame gameFrame;
	private int listX, listY, minionX, minionY;
	private HashMap<UUID, JLabel> minionMap = new HashMap<UUID,JLabel>();
	private Vector<JLabel> playerVector = new Vector<JLabel>();

	public GameGui(GameClientGuiInterface client) {
		this.client = client;
		listX = listY = 10;
		minionX = minionY = 0;
	}

	@Override
	public void openLoginWindow() {
		System.out.println("GUI: open login Window: ");
		loginFrame = new LoginFrame(client);
		//loginFrame.setVisible(true);
		
		/*
		Random random = new Random();
		String username = Float.toString(random.nextFloat());
		System.out.println("GUI: input username: " + username);

		System.out.println("GUI: push login button");
		if (!client.login(username)) {
			System.out.println("GUI: Login failed, try another username or check network!");
			System.out.println();
		}
		*/
	}

	@Override
	public void closeLoginWindow() {
		loginFrame.dispose();
		/*
		System.out.println("GUI: Login succeed!");
		System.out.println("GUI: close login window:");
		System.out.println();
		*/
	}
/*
	@Override
	public void openGameWindow(Minion minion) {
		gameFrame = new GameFrame(client);
		gameFrame.setVisible(true);
		drawMinion(minion.x, minion.y, minion.minionID);
        drawScores(minion.userScores);
		//closeGameWindow();
	}
*/
	
	public void openGameWindow(Room room) {
		gameFrame = new GameFrame(client);
		gameFrame.setVisible(true);
		 Iterator it = room.getMinions().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<UUID, Minion> pair = (Map.Entry<UUID, Minion>)it.next();
	            Minion minion_t = pair.getValue();
	            final JLabel minionLabel = new JLabel(minion_t.getMinionID().toString());
	            minionLabel.addMouseListener(new MouseAdapter() {
	                public void mouseClicked(MouseEvent e) {
	                    client.feedMinion(minion_t.getMinionID());
	                    //minionLabel.setVisible(false);
	                }
	            }
	            );
	            ImageIcon imgIcon = new ImageIcon(this.getClass().getResource("Minion-Dancing-icon-small.png"));
	            minionLabel.setIcon(imgIcon);
	            minionLabel.setBounds((int)(minion_t.getX()*(gameFrame.getWidth() - 128)), (int)(minion_t.getY()*(gameFrame.getHeight() - 134)), 128, 134);
	            minionMap.put(minion_t.getMinionID(), minionLabel);
	            drawMinion(minion_t.getX(), minion_t.getY(), minion_t.getMinionID());
	        }
	        /*for (Minion minion : room.getMinions()) {
	            drawMinion(minion.getX(), minion.getY(), minion.getMinionID());    
	        }*/
	         
	        drawScores(room.getPlayers());
	}

	@Override
	public void closeGameWindow() {
		client.logout();
		gameFrame.dispose();
		System.out.println("GUI: close game window!");
	}

	@Override
	public void drawMinion(float x, float y, UUID minionId) {
		System.out.println("GUI: receive a new minion: x: " + x + ", y: " + y + ", ID: " + minionId);
		//this.minionID = minionId;
		final String id = minionId.toString();
		//final JLabel minionLabel = new JLabel(id);
		final UUID minionUUID = minionId;
		//if(minionMap.size() == 3) {
			JLabel minionLabel = minionMap.get(minionId);
			gameFrame.getContentPane().remove(minionLabel);
		
			/*
			minionLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					client.feedMinion(UUID.fromString(id));
					minionLabel.setVisible(false);
					minionMap.remove(minionUUID);
				}
			});
			*/
			
			//ImageIcon imgIcon = new ImageIcon(this.getClass().getResource("Minion-Dancing-icon-small.png"));
			//minionLabel.setIcon(imgIcon);
			minionLabel.setBounds((int)(x*(gameFrame.getWidth() - 128)), (int)(y*(gameFrame.getHeight() - 134)), 128, 134);
			gameFrame.getContentPane().add(minionLabel);
		//}
		//else {
			//minionMap.put(minionId, minionLabel);
			//minionLabel.setBounds((int)x, (int)y, 128, 134);
			//gameFrame.getContentPane().add(minionLabel);
		//}
	}

	@Override
	public void drawScores(Map<String, Player> players) {

		System.out.println("GUI: Prepare for the Scores(" + players.size() + "):");
		Map<String, Player> players_t = Util.sortPlayerByScoreDesc(players);
		
		try {
			int gamerOrder = 1;
			Iterator it = players_t.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Player> pair = (Map.Entry<String, Player>)it.next();
				Player player_t = pair.getValue();
				drawScore(player_t.getName(), player_t.getScore(), gamerOrder);
				gamerOrder++;
			}
			/*for (Player player : players) {
				drawScore(player.getName(), player.getScore(), gamerOrder);
				gamerOrder++;
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawScore(String username, int score, int gamerOrder) {
		System.out.println("GUI: username: " + username + ", score: " + score + ", game oder: " + gamerOrder);
		
		JLabel lbGamerOrder = new JLabel(Integer.toString(gamerOrder));
		lbGamerOrder.setBounds(listX, listY, 14, 15);
		gameFrame.getContentPane().add(lbGamerOrder);
		System.out.printf("lbPlayer added"+ lbGamerOrder.getText()+"\n");
		listX = listX + 20;
		JLabel lbPlayer = new JLabel(username+": ");
		lbPlayer.setBounds(listX, listY, 54, 15);
		gameFrame.getContentPane().add(lbPlayer);
		System.out.printf("lbPlayer added"+ lbPlayer.getText()+"\n");
		listX = listX + 100;
		JLabel lbScore = new JLabel(Integer.toString(score));
		lbScore.setBounds(listX, listY, 54, 15);
		gameFrame.getContentPane().add(lbScore);
		System.out.printf("lblScore added"+ lbScore.getText()+"\n");
		listX = 10;
		listY = listY + 20;
		playerVector.add(lbGamerOrder);
		playerVector.add(lbPlayer);
		playerVector.add(lbScore);
		gameFrame.revalidate();
		gameFrame.repaint();
	}

	@Override
	public void cleanScreen() {
		System.out.println("clean screen");
		listX = listY = 10;
		minionX = minionY = 0;
		Iterator<JLabel> it = playerVector.iterator();
		while(it.hasNext()) {
			JLabel tempLabel = it.next();
			gameFrame.getContentPane().remove(tempLabel);
		}
	}

	@Override
	public void drawNotification(String notification) {
		System.out.println("GUI: " + notification);
		//MessageFrame notifyFrame = new MessageFrame(notification);
	}

	@Override
	public void run() {

	}
	
	@Override
	public GameFrame getGameFrame() {
		return this.gameFrame;
	}
}
