package ui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Info extends JFrame {
    public static void createGUI() {
        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        Font font = new Font("Verdana", Font.PLAIN, 12);

        JPanel htmlPanel = new JPanel();

        String text = "<html><h2>What is Google Labs?</h2>" +
                "<font face=’verdana’ size = 2>" +
                " Google Labs is a playground <br>";

        font = new Font(null, Font.PLAIN, 10);

        JLabel htmlLabel = new JLabel();
        htmlLabel.setText(text);
        htmlLabel.setFont(font);
        htmlPanel.add(htmlLabel);

        mainPanel.add(htmlPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setPreferredSize(new Dimension(450, 485));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                createGUI();
            }
        });
    }

}
