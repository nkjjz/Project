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
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            int returnVal = chooser.showOpenDialog(this);
            System.out.println("returnVal=" + returnVal);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filepath = chooser.getSelectedFile().getAbsolutePath();
                System.out.println(filepath);
                System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                gameController.loadGameFromFile(filepath);
                repaint();
            }
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
                chessboard.getClickController().forStart();
                chessboard.initiateEmptyChessboard();
                chessboard.inti();
                chessboard.setCurrentColor(ChessColor.BLACK);
                repaint();
            }
        });
    }

    static JLabel ActiveContainer = new JLabel();
    public void addActiveContainer(){
        ActiveContainer.setText(ClickController.round+" || counter: "+ClickController.b);
        ActiveContainer.setLocation(HEIGTH,HEIGTH/10);
        ActiveContainer.setSize(200,60);
        ActiveContainer.setFont(new Font("Rockwell",Font.PLAIN,16));
        ActiveContainer.setForeground(Color.YELLOW);
        add(ActiveContainer);
    }

    public static void setActiveContainer(){
        ActiveContainer.setText(ClickController.round+" || counter: "+ClickController.b);
    }

    private void addRegretButton(){
        JButton button = new JButton("Regret");
        button.setLocation(HEIGTH,HEIGTH/10+480);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
                    ClickController.rejiluQiJu();
                    if (ClickController.QiJu.size()!=0) {
                        ClickController.cnt--;
                        if (ClickController.cnt % 2 == 0) {
                            ClickController.b--;
                            ActiveContainer.setText("Round Black || counter: " + ClickController.b);
                        } else {
                            ActiveContainer.setText("Round White || counter: " + ClickController.b);
                        }
                    }else {
                        ActiveContainer.setText("Round Black || counter: 0");
                    }
                }
        );
    }

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
