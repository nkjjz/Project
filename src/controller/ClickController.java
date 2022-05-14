package controller;


import model.*;
import view.ChessGameFrame;
import view.Chessboard;

import javax.management.QueryExp;
import javax.swing.*;
import java.util.ArrayList;

public class ClickController {
    public static Chessboard chessboard;
    private ChessComponent first;
    public static String round = "Round Black";
    public static int cnt;
    public ChessComponent chessComponent[][];


    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) {
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                jiluQiJu(chessboard.getChessComponents());
                cnt++;
                if (cnt%2==0)
                    round = "Round Black";
                else round = "Round White";
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;
            }
        }
        ChessGameFrame.setActiveContainer();
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    public static ArrayList<String> QiJu = new ArrayList<>();
    public static void jiluQiJu(ChessComponent[][] chessComponents){
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j]instanceof EmptySlotComponent) {
                    a.append("E");
                }else if (chessComponents[i][j]instanceof BishopChessComponent&&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append("B");
                }else if (chessComponents[i][j]instanceof BishopChessComponent&&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append("b");
                }else if (chessComponents[i][j]instanceof KingChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append("K");
                }else if (chessComponents[i][j]instanceof KingChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append("k");
                }else if (chessComponents[i][j]instanceof KnightChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append("N");
                }else if (chessComponents[i][j]instanceof KnightChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append("k");
                }else if (chessComponents[i][j]instanceof PawnChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append("P");
                }else if (chessComponents[i][j]instanceof PawnChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append("p");
                }else if (chessComponents[i][j]instanceof QueenChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append("Q");
                }else if (chessComponents[i][j]instanceof QueenChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append("q");
                }else if (chessComponents[i][j]instanceof RookChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append("R");
                }else if (chessComponents[i][j]instanceof RookChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append("r");
                }
            }
            QiJu.add(a.toString());
        }

    }
    public static void rejiluQiJu(){
        if (QiJu.size()==0){
            JOptionPane.showMessageDialog(null,"已不能悔棋");
        }else {
            String shanghuihe = QiJu.get(QiJu.size()-1);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessboard.reput(shanghuihe.charAt(i*8+j),i,j,chessboard.getChessComponents());
                }
            }
            chessboard.swapColor();
            QiJu.remove(QiJu.size()-1);
            cnt--;
        }
    }
}
