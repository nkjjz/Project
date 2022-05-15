package view;

import javax.swing.*;
import java.awt.*;

public class ColorSetter extends JFrame {
    public static Color color = new Color(255, 150, 50);
    public static Color chessColor1 = Color.BLACK;
    public static Color chessColor2 = Color.WHITE;
    public ColorSetter(int frameSize){
        this.setTitle("PlayerSetter");
        this.setLayout(null);
        Insets inset = this.getInsets();
        this.setSize((int)((frameSize + inset.left + inset.right) * 0.5), (int)((frameSize + inset.top + inset.bottom) * 0.3));
        this.setLocationRelativeTo(null);

        JLabel boardColor = new JLabel();
        boardColor.setSize((int)(this.getWidth() * 0.4) , (int)(this.getHeight() * 0.2));
        boardColor.setLocation((int)(this.getWidth() *0.1) , 10);
        boardColor.setText("BoardColor:");
        boardColor.setFont(new Font("Calibri", Font.BOLD, 20));
        boardColor.setVisible(true);
        add(boardColor);

        JComboBox<String> boardColorSetter = new JComboBox<>();
        boardColorSetter.addItem("Tradition");
        boardColorSetter.addItem("Dark");
        boardColorSetter.addItem("Light");
        boardColorSetter.setLocation(boardColor.getWidth(), 23);
        boardColorSetter.setFont(new Font("Calibri", Font.BOLD, 15));
        boardColorSetter.setSize((int)(this.getWidth() * 0.4) , (int)(this.getHeight() * 0.1));
        add(boardColorSetter);
        boardColorSetter.addActionListener(e -> {
            if (boardColorSetter.getSelectedIndex() == 0) {
                color = new Color(255, 150, 50);
            } else if (boardColorSetter.getSelectedIndex()==1 ) {
                color = new Color(102 , 88 , 86);
            } else if (boardColorSetter.getSelectedIndex() == 2) {
                color = Color.pink;
            }
        });;


        JLabel chessColor = new JLabel();
        chessColor.setSize((int)(this.getWidth() * 0.4) , (int)(this.getHeight() * 0.2));
        chessColor.setLocation((int)(this.getWidth() *0.1) , 80);
        chessColor.setText("ChessColor:");
        chessColor.setFont(new Font("Calibri", Font.BOLD, 20));
        chessColor.setVisible(true);
        add(chessColor);

        JComboBox<String> chessColorSetter = new JComboBox<>();
        chessColorSetter.addItem("Tradition");
        chessColorSetter.addItem("Red & Green");
        chessColorSetter.setLocation(boardColor.getWidth(), 93);
        chessColorSetter.setFont(new Font("Calibri", Font.BOLD, 15));
        chessColorSetter.setSize((int)(this.getWidth() * 0.4) , (int)(this.getHeight() * 0.1));
        add(chessColorSetter);
        chessColorSetter.addActionListener(e -> {
            if (chessColorSetter.getSelectedIndex() == 0) {
                chessColor1 = Color.BLACK;
                chessColor2 = Color.WHITE;

            } else if (chessColorSetter.getSelectedIndex()==1 ) {
                chessColor1 = Color.RED;
                chessColor2 = Color.green;
            }
        });

        JButton confirmBtn = new JButton("confirm");
        confirmBtn.setSize(120 , 30);
        confirmBtn.setFont(new Font("Calibri", Font.ITALIC, 20));
        confirmBtn.setLocation((int)(this.getWidth() * 0.5 - confirmBtn.getWidth() * 0.5) , 160);
        confirmBtn.setVisible(true);
        add(confirmBtn);
        confirmBtn.addActionListener(e -> {
            dispose();
            ChessGameFrame mainFrame = new ChessGameFrame(1000,760);
            mainFrame.setVisible(true);
        });
    }

}
