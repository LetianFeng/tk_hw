package guip3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SubscriptionButton extends JButton {

    private Point activePoint = new Point(0,0);
    private boolean subscribe = false;

    public void updateButton(Point activePoint) {
        this.activePoint = activePoint;
        this.setBounds(activePoint.x, activePoint.y, 200, 30);
    }

    public SubscriptionButton() {
        this.setBounds(activePoint.x, activePoint.y, 200, 30);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // make new subscription
            }
        });
    }
}
