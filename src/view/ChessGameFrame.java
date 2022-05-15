package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addHelloButton();
        addLoadButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

    //将军时提示
    public static void addPrompt(Chessboard chessboard){
        if (chessboard.getCurrentColor() == ChessColor.BLACK) {
            JOptionPane.showMessageDialog(null, "白方被将军啦", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else if (chessboard.getCurrentColor() == ChessColor.WHITE) {
            JOptionPane.showMessageDialog(null, "黑方被将军啦", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void showWinner(Chessboard chessboard){
        if (chessboard.getCurrentColor() == ChessColor.BLACK) {
            JOptionPane.showMessageDialog(null, "黑方获胜！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else if (chessboard.getCurrentColor() == ChessColor.WHITE) {
            JOptionPane.showMessageDialog(null, "白方获胜！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static int pawnPromotion(){
        String[] option = {"后","车","象","马"};
        int response = JOptionPane.showOptionDialog(null, "请选择您想升变的类型\n", "小兵可以变身啦", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, "后");
        return response;
    }

    public static void promptOfBeingCaptured(){
        JOptionPane.showMessageDialog(null, "这样会被将军噢 想想其他的走法吧~", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

}
