package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static controller.ClickController.chessboard;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    public static int getI;
    private JLabel playerLabel;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public int i ;

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();
        inti();

    }


    public void inti(){

        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initPawnOnBoard(1,0,ChessColor.BLACK);
        initPawnOnBoard(1,1,ChessColor.BLACK);
        initPawnOnBoard(1,2,ChessColor.BLACK);
        initPawnOnBoard(1,3,ChessColor.BLACK);
        initPawnOnBoard(1,4,ChessColor.BLACK);
        initPawnOnBoard(1,5,ChessColor.BLACK);
        initPawnOnBoard(1,6,ChessColor.BLACK);
        initPawnOnBoard(1,7,ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE-2,0,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE-2,1,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE-2,2,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE-2,3,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE-2,4,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE-2,5,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE-2,6,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE-2,7,ChessColor.WHITE);
        initKingOnBoard(0, 3,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE-1, 3,ChessColor.WHITE);
        initQueenOnBoard(0, 4,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE-1, 4,ChessColor.WHITE);
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(0,5,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,5,ChessColor.WHITE);
        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(0,6,ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE-1,6,ChessColor.WHITE);

    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        SoundTest soundTest = new SoundTest("Music/ChessSound.mp3");
        soundTest.start();

        chess1.repaint();
        chess2.repaint();

    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = (currentColor == ChessColor.BLACK) ? ChessColor.WHITE : ChessColor.BLACK;
    }


    public int getI(){
        if (currentColor==ChessColor.BLACK){
            return i=0;
        }else {
            return i=1;
        }
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row,int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, 3), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row,int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, 4), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initEmptyOnBoard(int row,int col,ChessColor color){
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void reput(Character a,int i,int j,ChessComponent[][] chess){
        if (a=='-'){
            initEmptyOnBoard(i,j,ChessColor.NONE);
        }else if (a=='B'){
            initBishopOnBoard(i,j,ChessColor.BLACK);
        }else if (a=='b'){
            initBishopOnBoard(i,j,ChessColor.WHITE);
        }else if (a=='K'){
            initKingOnBoard(i,j,ChessColor.BLACK);
        }else if (a=='k'){
            initKingOnBoard(i,j,ChessColor.WHITE);
        }else if (a=='P'){
            initPawnOnBoard(i,j,ChessColor.BLACK);
        }else if (a=='p'){
            initPawnOnBoard(i,j,ChessColor.WHITE);
        }else if (a=='Q'){
            initQueenOnBoard(i,j,ChessColor.BLACK);
        }else if (a=='q'){
            initQueenOnBoard(i,j,ChessColor.WHITE);
        }else if (a=='N'){
            initKnightOnBoard(i,j,ChessColor.BLACK);
        }else if (a=='n'){
            initKnightOnBoard(i,j,ChessColor.WHITE);
        }else if (a=='R'){
            initRookOnBoard(i,j,ChessColor.BLACK);
        }else if (a=='r'){
            initRookOnBoard(i,j,ChessColor.WHITE);
        }
        chess[i][j].repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);

        initiateEmptyChessboard();
        String a = ClickController.jiluQiJu(chessboard.getChessComponents());
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i)=='-'){

            }else if (a.charAt(i)=='B'){

            }else if (a.charAt(i)=='b'){

            }else if (a.charAt(i)=='K'){

            }else if (a.charAt(i)=='k'){

            }else if (a.charAt(i)=='P'){

            }else if (a.charAt(i)=='p'){

            }else if (a.charAt(i)=='Q'){

            }else if (a.charAt(i)=='q'){

            }else if (a.charAt(i)=='R'){

            }else if (a.charAt(i)=='r'){

            }else if (a.charAt(i)=='N'){

            }else if (a.charAt(i)=='n'){

            }
        }
    }
    public ChessComponent getKingOfRival(ChessColor currentPlayer){ // 获得对方王的棋子
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (chessComponents[i][j] instanceof KingChessComponent){
                    if (chessComponents[i][j].getChessColor() != currentPlayer){
                        return chessComponents[i][j];
                    }
                }
            }
        }
        return null;
    }

    public boolean captureKing(ChessColor currentPlayer){ //判断对方是否被将军
        List<ChessComponent> list = getPlayerChessComponents(currentPlayer);
        for (ChessComponent i : list){
            if (i.canMoveTo(getChessComponents(), getKingOfRival(currentPlayer).getChessboardPoint())){
                return true;
            }
        }
        return false;
    }

    public List<ChessComponent> getPlayerChessComponents(ChessColor currentPlayer){ //获得行棋方所有的在棋盘上的棋子
        List<ChessComponent> list = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (!(getChessComponents()[i][j] instanceof EmptySlotComponent) && getChessComponents()[i][j].getChessColor() == currentPlayer){
                    list.add(getChessComponents()[i][j]);
                }
            }
        }
        return list;
    }

    public ChessComponent promotePawn(int response, ChessComponent chessComponent){
        switch (response) {
            case 0 -> initQueenOnBoard(chessComponent.getChessboardPoint().getX(), chessComponent.getChessboardPoint().getY(), chessComponent.getChessColor());
            case 1 -> initRookOnBoard(chessComponent.getChessboardPoint().getX(), chessComponent.getChessboardPoint().getY(), chessComponent.getChessColor());
            case 2 -> initBishopOnBoard(chessComponent.getChessboardPoint().getX(), chessComponent.getChessboardPoint().getY(), chessComponent.getChessColor());
            case 3 -> initKnightOnBoard(chessComponent.getChessboardPoint().getX(), chessComponent.getChessboardPoint().getY(), chessComponent.getChessColor());
        }
        return chessComponents[chessComponent.getChessboardPoint().getX()][chessComponent.getChessboardPoint().getY()];
    }
}
