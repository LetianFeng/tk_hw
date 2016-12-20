package guip3;

import client.BlogMessage;
import client.ClientConfig;
import client.TopicMgtItem;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogBox extends JPanel {

    final static int pixelPerLine = 17;
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final private BlogMessage blogMessage;
    final private String messageContent;
    final private int startY;
    final private int width;
    final private WeiboFrame mainFrame;
    private Point activeTopicPoint = new Point(0, 0);

    public BlogBox (BlogMessage blogMessage, int x, int y, int width, WeiboFrame mainFrame) {
        this.startY = y;
        this.width = width;
        this.blogMessage = blogMessage;
        this.messageContent = blogMessage.getContent();
        this.mainFrame = mainFrame;

        JTextArea messageArea = new JTextArea(messageContent);
        messageArea.setBounds(0, 30, width, 1000);
        messageArea.setFont(new Font("Dialog", Font.PLAIN, 12));

        int lineCounter = countLines(messageArea);
        messageArea.setSize(width, lineCounter * pixelPerLine);
        int height = lineCounter * pixelPerLine + 40;

        this.setLayout(null);
        this.setBounds(x, y, width, height);
        initialize(messageArea);
        this.setVisible(true);
    }

    private void initialize(JTextArea messageArea) {
        drawAvatar();
        drawUserName();
        drawDate();
        drawMessageArea(messageArea);
        drawUserSubscriptionButton(blogMessage.getSender());
    }

    private void drawAvatar() {
        JLabel avatarLabel = new JLabel();
        avatarLabel.setBounds(0, 0, 20, 20);

        String avatarPath = Constant.smallAvatar.get(blogMessage.getAvatar());
        Image avatarImg = new ImageIcon(this.getClass().getResource(avatarPath)).getImage();
        avatarLabel.setIcon(new ImageIcon(avatarImg));
        avatarLabel.setVisible(true);
        this.add(avatarLabel);
    }

    private void drawMessageArea(JTextArea messageArea) {

        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        messageArea.getCaret().deinstall(messageArea);
        messageArea.setFont(messageArea.getFont().deriveFont(14f));

        for (String topic : parseContent(messageContent)) {
            int startIndex = messageContent.indexOf(topic);
            int endIndex = startIndex + topic.length();
            try {
                messageArea.getHighlighter().addHighlight(startIndex, endIndex, DefaultHighlighter.DefaultPainter);
                Point startPosition = messageArea.modelToView(startIndex).getLocation();
                Point endPosition = messageArea.modelToView(endIndex).getLocation();
                drawTopicSubscriptionButton(startPosition, endPosition, topic.substring(1,topic.length()-1));
            } catch (BadLocationException ble) {
                System.out.print("Bad Location for Message Highlight!");
            }
            while (startIndex >= 0) {
                startIndex = messageContent.indexOf(topic, startIndex + 1);
                if (startIndex >= 0) {
                    endIndex = startIndex + topic.length();
                    try {
                        messageArea.getHighlighter().addHighlight(startIndex, endIndex, DefaultHighlighter.DefaultPainter);
                        Point startPosition = messageArea.modelToView(startIndex).getLocation();
                        Point endPosition = messageArea.modelToView(endIndex).getLocation();
                        drawTopicSubscriptionButton(startPosition, endPosition, topic.substring(1,topic.length()-1));
                    } catch (BadLocationException ble) {
                        System.out.print("Bad Location for Message Highlight!");
                    }
                }
            }
        }
        this.add(messageArea);
    }

    private void drawUserName() {
        JLabel userNameLabel = new JLabel(blogMessage.getSender());
        userNameLabel.setBounds(30, 0, 200, 20);
        userNameLabel.setVisible(true);
        this.add(userNameLabel);
    }

    private void drawDate() {
        String dateString = dateFormatter.format(blogMessage.getDate());
        JLabel timeLabel = new JLabel(dateString);
        timeLabel.setBounds(300, 0, width - 300, 20);
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

    private static int countLines(JTextArea textArea)
    {
        AttributedString text = new AttributedString(textArea.getText());
        text.addAttribute(TextAttribute.FONT, textArea.getFont());
        FontRenderContext frc = textArea.getFontMetrics(textArea.getFont()).getFontRenderContext();
        AttributedCharacterIterator charIt = text.getIterator();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
        Insets textAreaInsets = textArea.getInsets();
        float formatWidth = textArea.getWidth() - textAreaInsets.left - textAreaInsets.right;
        lineMeasurer.setPosition(charIt.getBeginIndex());

        int noLines = 0;
        while (lineMeasurer.getPosition() < charIt.getEndIndex())
        {
            lineMeasurer.nextLayout(formatWidth);
            noLines++;
        }
        return noLines;
    }

    private void drawTopicSubscriptionButton(Point startPosition, Point endPosition, final String topic) {
        if(endPosition.x - startPosition.x >= 0) {
            final JButton buttonPanel = new JButton();
            buttonPanel.setBounds(startPosition.x, startPosition.y + 30, endPosition.x - startPosition.x, pixelPerLine);
            buttonPanel.setVisible(true);
            buttonPanel.setOpaque(false);
            buttonPanel.setContentAreaFilled(false);
            buttonPanel.setBorderPainted(false);
            buttonPanel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    activeTopicPoint = buttonPanel.getLocation();
                    activeTopicPoint.y += startY;
                    List<TopicMgtItem> topicList = mainFrame.getClientAPI().getTopicManagementList(ClientConfig.TOPIC_PREFIX);
                    boolean subscriberStatus = false;
                    for(TopicMgtItem currentTopic : topicList) {
                        if(currentTopic.getTopic().equals(topic))
                            if(currentTopic.isSubscribed())
                                subscriberStatus = true;
                    }
                    mainFrame.getSubButton().setSubscribe(subscriberStatus);
                    mainFrame.getSubButton().setCurrentTopic("t."+topic);
                    mainFrame.getSubButton().updateButton(activeTopicPoint);
                }
            });
            this.add(buttonPanel);
        }
        else {
            final JButton buttonPanel1 = new JButton();
            buttonPanel1.setBounds(startPosition.x, startPosition.y + 30, width - startPosition.x, pixelPerLine);
            buttonPanel1.setVisible(true);
            buttonPanel1.setOpaque(false);
            buttonPanel1.setContentAreaFilled(false);
            buttonPanel1.setBorderPainted(false);
            buttonPanel1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    activeTopicPoint = buttonPanel1.getLocation();
                    activeTopicPoint.y += startY;
                    activeTopicPoint.x = width - 80;
                    List<TopicMgtItem> topicList = mainFrame.getClientAPI().getTopicManagementList(ClientConfig.TOPIC_PREFIX);
                    boolean subscriberStatus = false;
                    for(TopicMgtItem currentTopic : topicList) {
                        if(currentTopic.getTopic().equals(topic))
                            if(currentTopic.isSubscribed())
                                subscriberStatus = true;
                    }
                    mainFrame.getSubButton().setSubscribe(subscriberStatus);
                    mainFrame.getSubButton().setCurrentTopic("t."+topic);
                    mainFrame.getSubButton().updateButton(activeTopicPoint);
                }
            });
            this.add(buttonPanel1);
            final JButton buttonPanel2 = new JButton();
            buttonPanel2.setBounds(0, endPosition.y + 30, endPosition.x, pixelPerLine);
            buttonPanel2.setVisible(true);
            this.add(buttonPanel2);
            buttonPanel2.setOpaque(false);
            buttonPanel2.setContentAreaFilled(false);
            buttonPanel2.setBorderPainted(false);
            buttonPanel2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    activeTopicPoint = buttonPanel2.getLocation();
                    activeTopicPoint.y += startY;
                    List<TopicMgtItem> topicList = mainFrame.getClientAPI().getTopicManagementList(ClientConfig.TOPIC_PREFIX);
                    boolean subscriberStatus = false;
                    for(TopicMgtItem currentTopic : topicList) {
                        if(currentTopic.getTopic().equals(topic))
                            if(currentTopic.isSubscribed())
                                subscriberStatus = true;
                    }
                    mainFrame.getSubButton().setSubscribe(subscriberStatus);
                    mainFrame.getSubButton().setCurrentTopic("t."+topic);
                    mainFrame.getSubButton().updateButton(activeTopicPoint);
                }
            });
        }
    }

    private void drawUserSubscriptionButton(final String userName) {
        final JButton buttonPanel = new JButton();
        buttonPanel.setBounds(0, 0, 80, 20);
        buttonPanel.setVisible(true);
        buttonPanel.setOpaque(false);
        buttonPanel.setContentAreaFilled(false);
        buttonPanel.setBorderPainted(false);
        buttonPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeTopicPoint = buttonPanel.getLocation();
                activeTopicPoint.y += startY;
                if(activeTopicPoint.y < 20)
                    activeTopicPoint.y += 40;
                List<TopicMgtItem> subscriberList = mainFrame.getClientAPI().getTopicManagementList(ClientConfig.USER_PREFEX);
                boolean subscriberStatus = false;
                for(TopicMgtItem subscriber : subscriberList) {
                    if(subscriber.getTopic().equals(userName))
                        if(subscriber.isSubscribed())
                            subscriberStatus = true;
                }
                mainFrame.getSubButton().setSubscribe(subscriberStatus);
                mainFrame.getSubButton().setCurrentTopic("u."+userName);
                mainFrame.getSubButton().updateButton(activeTopicPoint);
            }
        });
        this.add(buttonPanel);
    }
}
