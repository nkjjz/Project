package view;


import javax.swing.*;

import java.awt.*;

public class StartPanel {

        JFrame jFrame = new JFrame();

        public StartPanel() {

            jFrame.setTitle("ChessGame");
            jFrame.setSize(900,700);
            jFrame.setLocationRelativeTo(null);
            jFrame.getContentPane().setLayout(null);
            JPanel imPanel=(JPanel) jFrame.getContentPane();
            imPanel.setOpaque(false);
            ImageIcon icon=new ImageIcon("images/Background.jpg");
            JLabel label = new JLabel(icon);
            label.setBounds(0, 0, jFrame.getWidth(), jFrame.getHeight());
            icon.setImage(icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
            jFrame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));

            JLabel testword=new JLabel("国际象棋");
            testword.setFont(new Font("隶书", Font.PLAIN, 60));
            testword.setForeground(Color.yellow);
            testword.setBounds(jFrame.getWidth()/3,jFrame.getHeight()/3,jFrame.getWidth()/3,40);
            testword.setHorizontalAlignment(JLabel.CENTER);

            JButton button1 = new JButton("Start");
            button1.setFont(new Font("Impact",Font.PLAIN,60));
            button1.setForeground(Color.BLACK);
            button1.setBounds(200,400,200,70);
            button1.setHorizontalAlignment(JButton.CENTER);
            button1.addActionListener(e -> {
                ChessGameFrame mainFrame = new ChessGameFrame(1000,760);
                mainFrame.setVisible(true);
            });

            JButton button2 = new JButton("Exit");
            button2.setFont(new Font("Impact",Font.PLAIN,60));
            button2.setForeground(Color.BLACK);
            button2.setBounds(500,400,200,70);
            button2.setHorizontalAlignment(JButton.CENTER);
            button2.addActionListener(e -> {
                jFrame.dispose();
            });

            jFrame.setResizable(false);
            jFrame.getContentPane().add(testword);
            jFrame.getContentPane().add(button1);
            jFrame.getContentPane().add(button2);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jFrame.setVisible(true);
        }
    }
