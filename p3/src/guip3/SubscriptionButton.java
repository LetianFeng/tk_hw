package guip3;

import client.ClientAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubscriptionButton extends JButton {

    private SubscriptionButton subButton;
    private Point activePoint = new Point(0,20);
    private String currentTopic = "public";
    private boolean subscribe = false;

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public void setCurrentTopic(String currentTopic) {
        this.currentTopic = currentTopic;
    }

    public void updateButton(Point activePoint) {
        this.activePoint = activePoint;
        if(subscribe)
            this.setText("unfollow");
        else
            this.setText("follow");
        this.setBounds(activePoint.x + 10, activePoint.y - 20, 100, 20);
        this.setVisible(true);
    }

    public SubscriptionButton(final ClientAPI clientAPI) {
        subButton = this;
        this.setBounds(activePoint.x + 10, activePoint.y - 20, 100, 20);
        this.setText("follow");
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                subButton.setVisible(false);
                if(currentTopic.substring(0,2).equals("t.")) {
                    if(subscribe)
                        clientAPI.unSubscribeTopic(currentTopic.substring(2,currentTopic.length()));
                    else
                        clientAPI.subscribeTopic(currentTopic.substring(2,currentTopic.length()));
                }
                else {
                    if(subscribe)
                        clientAPI.unFollowPerson(currentTopic.substring(2,currentTopic.length()));
                    else
                        clientAPI.followPerson(currentTopic.substring(2,currentTopic.length()));
                }
            }
        });
    }
}
