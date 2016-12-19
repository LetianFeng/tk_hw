package guip3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubscriptionButton extends JButton {

    private Point activePoint = new Point(0,20);
    private boolean subscribe = false;
    private SubscriptionButton subButton;
    private String currentTopic = "";

    public void setCurrentTopic(String currentTopic) {
        this.currentTopic = currentTopic;
    }

    public void updateButton(Point activePoint) {
        this.activePoint = activePoint;
        if(subscribe)
            this.setText("unfollow");
        else
            this.setText("follow");
        this.setBounds(activePoint.x + 10, activePoint.y - 20, 80, 20);
        this.setVisible(true);
    }

    public SubscriptionButton() {
        subButton = this;
        this.setBounds(activePoint.x + 10, activePoint.y - 20, 80, 20);
        this.setText("follow");
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                subButton.setVisible(false);
                // make new subscription
                System.out.println(currentTopic);
            }
        });
    }
}
