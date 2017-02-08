package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

    private Gui gui;
    private int accountId;
    private JLabel accountMoneyLabel;

    public ButtonPanel(int accountId, Gui gui) {
        this.gui = gui;
        this.accountId = accountId;
    }

    public void initPanel() {

        this.setLayout(null);

        JLabel accountNameLabel = new JLabel("account " + String.valueOf(accountId+1));
        accountNameLabel.setBounds(this.getWidth()/6, 0, this.getWidth()/3, this.getHeight()/3);
        accountNameLabel.setVisible(true);
        this.add(accountNameLabel);

        accountMoneyLabel = new JLabel("");
        accountMoneyLabel.setBounds(this.getWidth()/2, 0, this.getWidth()*2/3, this.getHeight()/3);
        accountMoneyLabel.setVisible(true);
        this.add(accountMoneyLabel);

        JButton snapshotButton = new JButton("Snapshot");
        snapshotButton.setBounds(this.getWidth()/4, this.getHeight()/2, this.getWidth()/2, this.getHeight()/4);
        snapshotButton.setVisible(true);
        snapshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.sendMarker(accountId);
            }
        });
        this.add(snapshotButton);
    }

    public void updateMoney(int money) {

        accountMoneyLabel.setText("Money " + '$' + String.valueOf(money));
    }

}
