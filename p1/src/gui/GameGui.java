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

	private GameClientGuiInterface client;
	private LoginFrame loginFrame;
	private GameFrame gameFrame;
	private int listX, listY;
	private HashMap<UUID, JLabel> minionMap = new HashMap<UUID,JLabel>();
	private Vector<JLabel> playerVector = new Vector<JLabel>();
	private Vector<JLabel> notificationVector = new Vector<JLabel>();

	public GameGui(GameClientGuiInterface client) {
		this.client = client;
		listX = 30;
		listY = 30;
	}

	@Override
	public void openLoginWindow() {
		System.out.println("GUI: open login Window: ");
		loginFrame = new LoginFrame(client);
	}

	@Override
	public void closeLoginWindow() {
		loginFrame.dispose();
	}

	public void openGameWindow(Room room) {
		gameFrame = new GameFrame(client);
		gameFrame.setVisible(true);
		 Iterator it = room.getMinions().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<UUID, Minion> pair = (Map.Entry<UUID, Minion>)it.next();
	            Minion minion_t = pair.getValue();           
	            final JLabel minionLabel = createLabel(minion_t.getMinionID(), (int)minion_t.getX(), (int)minion_t.getY());
	            minionMap.put(minion_t.getMinionID(), minionLabel);
	            drawMinion(minion_t.getX(), minion_t.getY(), minion_t.getMinionID());
	        }
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
		JLabel minionLabel = minionMap.get(minionId);
		gameFrame.getContentPane().remove(minionLabel);
		minionLabel.setBounds((int)((x+0.5)*0.66*(Constant.gameFrameWidth-300)), 
				(int)(y*(Constant.gameFrameHeight - 100)), Constant.minionLabelWidth, Constant.minionLabelHeight);
		System.out.printf("x is "+x+" y is "+y);
		gameFrame.getContentPane().add(minionLabel);
	}

	@Override
	public void drawScores(Map<String, Player> players) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawScore(String username, int score, int gamerOrder) {
		JLabel lbGamerOrder = createLabel(Integer.toString(gamerOrder), listX, listY, 10,Constant.messageLabelHeight);
		gameFrame.getContentPane().add(lbGamerOrder);
		listX = listX + 20;
		JLabel lbPlayer = createLabel(username+": ", listX, listY, 100, Constant.messageLabelHeight);
		gameFrame.getContentPane().add(lbPlayer);
		listX = listX + 100;
		JLabel lbScore = createLabel(Integer.toString(score), listX, listY, 20, Constant.messageLabelHeight);
		gameFrame.getContentPane().add(lbScore);
		listX = 30;
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
		listX = listY = 30;
		Iterator<JLabel> it = playerVector.iterator();
		while(it.hasNext()) {
			JLabel tempLabel = it.next();
			gameFrame.getContentPane().remove(tempLabel);
		}
	}

	@Override
	public void drawNotification(String notification) {
		//new MessageFrame(notification);
		
		if(this.gameFrame != null) {
			JLabel notificationLabel = createLabel(notification, 150, 150, Constant.notificationLabelWidth,
					Constant.notificationLabelHeight);
			int notificationNumber = notificationVector.size();
			if(notificationNumber < Constant.maxNotificationNumber) {
				notificationLabel.setBounds(Constant.notificationStartX, Constant.notificationStartY+
						Constant.notificationLabelHeight*notificationNumber, 
						Constant.notificationLabelWidth,Constant.notificationLabelHeight);
				notificationVector.add(notificationLabel);
				gameFrame.getContentPane().add(notificationLabel);
			}
			else {
				JLabel tempLabel = notificationVector.get(0);
				gameFrame.getContentPane().remove(tempLabel);
				notificationVector.remove(tempLabel);
				gameFrame.getContentPane().add(notificationLabel);
				notificationVector.add(notificationLabel);
				for(int i = 0; i < notificationVector.size(); i++) {
					notificationVector.get(i).setBounds(Constant.notificationStartX, Constant.notificationStartY+
							Constant.notificationLabelWidth*i, Constant.notificationLabelWidth,
							Constant.notificationLabelHeight);
				}
				gameFrame.revalidate();
				gameFrame.repaint();
			}
		}
		else {
			new MessageFrame(notification);
		}
	}

	@Override
	public void run() {

	}
	
	@Override
	public GameFrame getGameFrame() {
		return this.gameFrame;
	}
	
	private JLabel createLabel(final UUID minionId, int x, int y) {
		JLabel minionLabel = new JLabel(minionId.toString());
		minionLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                client.feedMinion(minionId);
            }
        }
        );
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(Constant.minionIconName));
        minionLabel.setIcon(imgIcon);
        minionLabel.setBounds(x, y, Constant.minionLabelWidth, Constant.minionLabelHeight);
        //minionMap.put(minionId, minionLabel);
        return minionLabel;
	}
	
	private JLabel createLabel(String str, int x, int y, int length, int width) {
		JLabel lbl = new JLabel(str);
		lbl.setBounds(x, y, length, width);
		return lbl;
	}
}
