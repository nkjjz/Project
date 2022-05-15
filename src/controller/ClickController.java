package controller;


import model.*;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ClickController {
    public static Chessboard chessboard;
    private ChessComponent first;
    public static String round = "Round Black";
    public static int cnt;
    public static int b;
    public ChessComponent[][] chessComponent;


    public ClickController(Chessboard chessboard) {
        ClickController.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                //判断行棋方是否移动的是兵且是否到底线 若到底线则升变
                if (first instanceof PawnChessComponent){
                    if (arriveBaseline(first)){
                        chessboard.promotePawn(ChessGameFrame.pawnPromotion(), first).repaint();
                    }
                }
                //判断当前行棋方移动后是否被对方将军 若被将军 则不能移动该棋子并提示 第一步判断
                ChessColor rival = (chessboard.getCurrentColor() == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
                if (chessboard.captureKing(rival)){
                    ChessGameFrame.promptOfBeingCaptured();

                    chessboard.swapChessComponents(first, chessComponent);
                }else {

                    if (completelyCaptured(rival)){ //每次移动完都要判断对手是否被将死 若被将死 则返回胜方
                        ChessGameFrame.showWinner(chessboard);
                    }else {
                        //判断当前行棋方移动后是否将对方军 若将军则提示
                        if (chessboard.captureKing(chessboard.getCurrentColor())){
                            ChessGameFrame.addPrompt(chessboard);
                        }
                    }
                    chessboard.swapColor();// 更换行棋方
                    first.setSelected(false);
                    first = null;
                }

            }
        }
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
    public static String jiluQiJu(ChessComponent[][] chessComponents){
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j]instanceof EmptySlotComponent) {
                    a.append('-');
                }else if (chessComponents[i][j]instanceof BishopChessComponent&&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append('B');
                }else if (chessComponents[i][j]instanceof BishopChessComponent&&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append('b');
                }else if (chessComponents[i][j]instanceof KingChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append('K');
                }else if (chessComponents[i][j]instanceof KingChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append('k');
                }else if (chessComponents[i][j]instanceof KnightChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append('N');
                }else if (chessComponents[i][j]instanceof KnightChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append('k');
                }else if (chessComponents[i][j]instanceof PawnChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append('P');
                }else if (chessComponents[i][j]instanceof PawnChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append('p');
                }else if (chessComponents[i][j]instanceof QueenChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append('Q');
                }else if (chessComponents[i][j]instanceof QueenChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append('q');
                }else if (chessComponents[i][j]instanceof RookChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.BLACK){
                    a.append('R');
                }else if (chessComponents[i][j]instanceof RookChessComponent &&chessComponents[i][j].getChessColor()== ChessColor.WHITE){
                    a.append('r');
                }
            }
            QiJu.add(a + "\n");
            a.append("\n");
        }
        return a.toString();
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

    public boolean completelyCaptured(ChessColor rival){ //判断对手的王是否被将死
        ChessComponent kingOfRival = chessboard.getKingOfRival(chessboard.getCurrentColor());
        List<ChessboardPoint> canMovePoints = kingOfRival.getCanMovePoints(chessboard.getChessComponents(), rival);
        List<ChessComponent> chessComponentList = chessboard.getPlayerChessComponents(rival);
        int cnt = 0;
        if (canMovePoints.size() == 0){
            return false;
        }else {
            for (ChessboardPoint i : canMovePoints) {
                for (ChessComponent j : chessComponentList) {
                    if (kingOfRival.capturedByOthers(chessboard.getChessComponents(), i) && !j.canMoveTo(chessboard.getChessComponents(), i)) {
                        cnt++;
                    } else if (kingOfRival.capturedByPawn(chessboard.getChessComponents(), i) && !j.canMoveTo(chessboard.getChessComponents(), i)) {
                        cnt++;
                    }
                }
            }
        }
        return cnt == canMovePoints.size();
    }

    public boolean arriveBaseline(ChessComponent chessComponent){
        switch (chessComponent.getChessColor()){
            case BLACK:
                if (chessComponent.getChessboardPoint().getX() == 7){
                    return true;
                }
                break;
            case WHITE :
                if (chessComponent.getChessboardPoint().getX() == 0){
                    return true;
                }
                break;
        }
        return false;
    }
}
