package guip3;

import client.BlogMessage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
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

    final static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
    final private BlogMessage blogMessage;
    final private String messageContent;
    final private int width, height;

    public BlogBox (BlogMessage blogMessage, int x, int y, int width) {
        this.width = width;
        this.blogMessage = blogMessage;
        this.messageContent = blogMessage.getContent();

        JTextArea messageArea = new JTextArea(messageContent);
        messageArea.setBounds(0, 30, width, 1000);

        int lineCounter = countLines(messageArea);
        messageArea.setSize(width, lineCounter * 18);
        height = lineCounter * 18 + 40;

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
    }

    private void drawAvatar() {
        JLabel avatarLabel = new JLabel();
        avatarLabel.setBounds(0, 0, 20, 20);

        String avatarPath = Constant.smallAvatar.get(blogMessage.getAvatar());
        Image AvatarImg = new ImageIcon(this.getClass().getResource(avatarPath)).getImage();
        avatarLabel.setIcon(new ImageIcon(AvatarImg));
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
            } catch (BadLocationException ble) {
                System.out.print("Bad Location for Message Highlight!");
            }
        }
        this.add(messageArea);
    }

    public void drawUserName() {
        JLabel userNameLabel = new JLabel(blogMessage.getSender());
        userNameLabel.setBounds(30, 0, 200, 20);
        userNameLabel.setVisible(true);
        this.add(userNameLabel);
    }

    public void drawDate() {
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

}
