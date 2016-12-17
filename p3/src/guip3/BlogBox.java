package guip3;

import client.BlogMessage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogBox extends JPanel {

    final static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
    final private BlogMessage blogMessage;
    final private String messageContent;
    final private int width, height;

    public BlogBox (BlogMessage blogMessage, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.blogMessage = blogMessage;
        this.messageContent = blogMessage.getContent();

        initialize();

        this.setBounds(x, y, width, height);
        this.setVisible(true);
    }

    private void initialize() {
        drawAvatar();
        drawUserName();
        drawDate();
        drawMessageArea();
    }

    private void drawAvatar() {
        JLabel avatarLabel = new JLabel();
        avatarLabel.setBounds(10,5,20, 20);

        String avatarPath = Constant.smallAvatar.get(blogMessage.getAvatar());
        Image AvatarImg = new ImageIcon(this.getClass().getResource(avatarPath)).getImage();
        avatarLabel.setIcon(new ImageIcon(AvatarImg));
        avatarLabel.setVisible(true);
        this.add(avatarLabel);
    }

    private void drawMessageArea() {

        JTextArea messageArea = new JTextArea(messageContent);
        messageArea.setBounds(0, 0, width - 100, height - 10);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        messageArea.getCaret().deinstall(messageArea);
        messageArea.setFont(messageArea.getFont().deriveFont(14f));

        for (String topic : parseContent(messageContent)) {
            int startIndex = messageContent.indexOf(topic);
            int endIndex = startIndex + topic.length();
            try {
                messageArea.getHighlighter().addHighlight(startIndex, endIndex, DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException ble) {
                System.out.print("Bad Location for Message Highlight!");
            }
        }
        this.add(messageArea);
    }

    public void drawUserName() {
        JLabel userNameLabel = new JLabel(blogMessage.getSender());
        userNameLabel.setBounds(10, 10, 100, 20);
        userNameLabel.setVisible(true);
        this.add(userNameLabel);
    }

    public void drawDate() {
        String dateString = dateFormatter.format(blogMessage.getDate());
        JLabel timeLabel = new JLabel(dateString);
        timeLabel.setBounds(300, 10, 300, 20);
        timeLabel.setVisible(true);
        this.add(timeLabel);
    }

    private List<String> parseContent(String messageContent) {
        Pattern pattern = Pattern.compile("\\#.+?\\#");
        Matcher matcher = pattern.matcher(messageContent);
        List<String> listMatches = new ArrayList<>();
        while(matcher.find()) {
            listMatches.add(matcher.group(0));
        }
        return listMatches;
    }

}
