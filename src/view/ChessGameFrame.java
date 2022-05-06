package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private ChessColor currentColor = ChessColor.BLACK;


    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addHelloButton();
        addLoadButton();
        addRestartButton();
        addRepentanceButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */

    public ChessColor getCurrentColor() {
        return currentColor;
    }


    private void addLabel() {
        JLabel statusLabel1 = new JLabel("Turn for "+getCurrentColor());
        statusLabel1.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel1.setSize(200, 60);
        statusLabel1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel1);

    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
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
            System.out.println("Restart");
            int result = JOptionPane.showConfirmDialog(null, "Are you sure to restart", "restart", JOptionPane.OK_CANCEL_OPTION);
            if (result==0){

            }else {

            }

        });
    }

    private void addRepentanceButton(){
        JButton button = new JButton("Repentance");
        button.setLocation(HEIGTH,HEIGTH/10+480);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Repentance");
            int result = JOptionPane.showConfirmDialog(null, "Are you sure to repentance", "repentance", JOptionPane.OK_CANCEL_OPTION);
            if (result==0){

            }else {

            }

        });
    }

}
