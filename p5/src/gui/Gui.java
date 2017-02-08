package gui;

import account.Account;
import message.MarkerMessage;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private static int[] ports = {30000, 30001, 30002};
    private JScrollPane jsp;
    private JTextArea monitorOutput;
    private ButtonPanel buttonAccount0;
    private ButtonPanel buttonAccount1;
    private ButtonPanel buttonAccount2;
    private Account account0;
    private Account account1;
    private Account account2;

    public Gui() {

        initInterface();

        account0 = new Account(ports[0], new int[]{ports[1],ports[2]}, this, 0);
        account1 = new Account(ports[1], new int[]{ports[2],ports[0]}, this, 1);
        account2 = new Account(ports[2], new int[]{ports[0],ports[1]}, this, 2);
        (new Thread(account0)).start();
        (new Thread(account1)).start();
        (new Thread(account2)).start();
    }

    private void initInterface() {

        this.setTitle("Snapshot Algorithm Show");
        this.setBounds(300,300,700,400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        jsp = new JScrollPane();
        monitorOutput = new JTextArea("");
        jsp.setViewportView(monitorOutput);
        jsp.setBounds(0, 0, this.getWidth()*2/3, this.getHeight());
        monitorOutput.setVisible(true);
        monitorOutput.setFont(new Font("monospaced", Font.PLAIN, 12));
        this.add(jsp);
        jsp.setVisible(true);

        buttonAccount0 = new ButtonPanel(0, this);
        buttonAccount0.setBounds(this.getWidth()*2/3, 0, this.getWidth()/3, this.getHeight()/3);
        buttonAccount0.setVisible(true);
        buttonAccount0.initPanel();
        this.add(buttonAccount0);

        buttonAccount1 = new ButtonPanel(1, this);
        buttonAccount1.setBounds(this.getWidth()*2/3, this.getHeight()/3, this.getWidth()/3, this.getHeight()/3);
        buttonAccount1.setVisible(true);
        buttonAccount1.initPanel();
        this.add(buttonAccount1);

        buttonAccount2 = new ButtonPanel(2, this);
        buttonAccount2.setBounds(this.getWidth()*2/3, this.getHeight()*2/3, this.getWidth()/3, this.getHeight()/3);
        buttonAccount2.setVisible(true);
        buttonAccount2.initPanel();
        this.add(buttonAccount2);
    }

    public void output(String outputString) {

        monitorOutput.append(outputString + "\n");
    }

    public void updateMoney(int id, int money) {
        switch (id) {
            case 0: buttonAccount0.updateMoney(money); break;
            case 1: buttonAccount1.updateMoney(money); break;
            case 2: buttonAccount2.updateMoney(money); break;
            default: break;
        }
    }

    public void sendMarker(int id) {
    	MarkerMessage fakeMarker = new MarkerMessage(3);
        switch (id) {
            case 0 : account0.responseServerMessage(fakeMarker); break;
            case 1 : account1.responseServerMessage(fakeMarker); break;
            case 2 : account2.responseServerMessage(fakeMarker); break;
            default: break;
        }
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.setVisible(true);

        while(true) {

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int sum_money = gui.account0.getMoney() + gui.account1.getMoney() + gui.account2.getMoney();
            System.out.println("current sum of all three account is: " + String.valueOf(sum_money) + '.');
        }
    }

}
