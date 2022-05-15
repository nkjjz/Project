package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import model.ChessComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */

public class ChessGameFrame extends JFrame {
    private JLabel playerLabel;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private Chessboard chessboard;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo");
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        this.playerLabel = new JLabel();
        this.playerLabel.setLocation(HEIGTH, HEIGTH / 10 );
        this.playerLabel.setSize(200,60);
        this.playerLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        String path = "images/Background.jpg";
        ImageIcon background = new ImageIcon(path);
        JLabel label = new JLabel(background);
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));


        MusicTest musicTest = new MusicTest("Music/Music1.mp3");
        musicTest.start();


        add(playerLabel);
        addChessboard();
        addSaveButton();
        addLoadButton();
        addRestartButton();
        addRegretButton();
        addActiveContainer();

    }


    /**
     * 在游戏面板中添加棋盘
     */

    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addSaveButton() {
        JButton button = new JButton("Save");;
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener(e -> {
            System.out.println("Click save");
            gameController.saveFileData();
        });
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

    private void addRestartButton(){
        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH,HEIGTH/10+360);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click restart");
            int result = JOptionPane.showConfirmDialog(null, "Are you sure to restart", "restart", JOptionPane.OK_CANCEL_OPTION);
            if (result==0){
                chessboard.initiateEmptyChessboard();
                chessboard.inti();
                ClickController.round="Round Black";
                ClickController.cnt=0;
                repaint();
            }
        });
    }

    static JLabel ActiveContainer = new JLabel();
    public void addActiveContainer(){
        ActiveContainer.setText(ClickController.round+" || counter: "+ClickController.cnt);
        ActiveContainer.setLocation(HEIGTH,HEIGTH/10);
        ActiveContainer.setSize(200,60);
        ActiveContainer.setFont(new Font("Rockwell",Font.PLAIN,16));
        ActiveContainer.setForeground(Color.YELLOW);
        add(ActiveContainer);
    }

    public static void setActiveContainer(){
        ActiveContainer.setText(ClickController.round+" || counter: "+ClickController.cnt);
    }

    private void addRegretButton(){
        JButton button = new JButton("Regret");
        button.setLocation(HEIGTH,HEIGTH/10+480);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> JOptionPane.showMessageDialog(this,"regret successfully"));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClickController.rejiluQiJu();
            }
        });
    }


}
