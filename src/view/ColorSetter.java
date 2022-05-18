package view;

import javax.swing.*;
import java.awt.*;

public class ColorSetter extends JFrame {
    public static Color[] BACKGROUND_COLORS = {Color.WHITE, Color.BLACK};
    public ColorSetter(int frameSize){
        this.setTitle("主题选择");
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
        boardColorSetter.addItem("Bright");
        boardColorSetter.setLocation(boardColor.getWidth(), 23);
        boardColorSetter.setFont(new Font("Calibri", Font.BOLD, 15));
        boardColorSetter.setSize((int)(this.getWidth() * 0.4) , (int)(this.getHeight() * 0.1));
        add(boardColorSetter);
        boardColorSetter.addActionListener(e -> {
            if (boardColorSetter.getSelectedIndex() == 0) {
                BACKGROUND_COLORS = new Color[]{Color.WHITE, Color.BLACK};
            } else if (boardColorSetter.getSelectedIndex()==1 ) {
                BACKGROUND_COLORS = new Color[]{Color.GRAY, Color.BLACK};
            } else if (boardColorSetter.getSelectedIndex() == 2) {
                BACKGROUND_COLORS = new Color[]{Color.WHITE, Color.orange};
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
