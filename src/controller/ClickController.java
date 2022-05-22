package controller;


import model.*;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickController {
    public static Chessboard chessboard;
    private ChessComponent first;
    public static String round = "Round Black";
    public static int cnt;
    private int copyOfCnt;
    public static int b;
    private ChessboardPoint temp = null;
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
                showCanMovePoints(first);
            }
        } else {
            if (first == chessComponent) {
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
                notShowCanMovePoints(recordFirst);
            } else if (handleSecond(chessComponent)) {
                jiluQiJu(chessboard.getChessComponents());
                notShowCanMovePoints(first);
                cnt++;
                if (cnt%2==0) {
                    round = "Round Black";
                }
                else {
                    round = "Round White";
                    b++;
                }
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);

                ChessColor rival = (chessboard.getCurrentColor() == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;

                if (chessboard.captureKing(rival)){
                    ChessGameFrame.promptOfBeingCaptured();
                    chessboard.swapChessComponents2(first, chessComponent);
                }else {
                    if (first instanceof PawnChessComponent){
                        temp = first.haveAdjacentPawn(chessboard.getChessComponents());
                        if (arriveBaseline(first)){
                            ChessComponent recordFirst = first;
                            first = chessboard.promotePawn(ChessGameFrame.pawnPromotion(), recordFirst);
                            first.repaint();
                        }

                    }
                    //判断当前行棋方移动后是否将对方军 若将军则提示
                    if (chessboard.captureKing(chessboard.getCurrentColor())){
                        if (completelyCaptured(rival, first)){ //每次移动完都要判断对手是否被将死 若被将死 则返回胜方并restart
                            ChessGameFrame.showWinner(chessboard);
                            chessboard.getClickController().forStart();
                            chessboard.initiateEmptyChessboard();
                            chessboard.inti();
                            chessboard.setCurrentColor(ChessColor.BLACK);
                            chessboard.repaint();
                        }else {
                            ChessGameFrame.addPrompt(chessboard);
                            first.repaint();
                        }
                    }
                    chessboard.swapColor();// 更换行棋方
                }
                first.setSelected(false);
                first = null;
            }
        }
        ChessGameFrame.setActiveContainer();
    }

    public void showCanMovePoints(ChessComponent chessComponent){
        List<ChessboardPoint> list = chessComponent.getCanMovePoints(chessboard.getChessComponents(), chessboard.getCurrentColor());
        for (ChessboardPoint i : list){
            Graphics g = chessboard.getGraphics();
            g.setColor(Color.MAGENTA);
            ChessComponent temp = chessboard.getChessComponents()[i.getX()][i.getY()];
            if (handleSecond(temp)) {
                int x = temp.getX();
                int y = temp.getY();
                g.drawOval(x, y, temp.getWidth(), temp.getHeight());
            }
        }
    }

    public void notShowCanMovePoints(ChessComponent chessComponent){
        List<ChessboardPoint> list = chessComponent.getCanMovePoints(chessboard.getChessComponents(), chessboard.getCurrentColor());
        for (ChessboardPoint i : list){
            chessboard.getChessComponents()[i.getX()][i.getY()].repaint();
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
        if (first instanceof KingChessComponent){
            return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint()) &&
                    !first.capturedByPawn(chessboard.getChessComponents(), chessComponent.getChessboardPoint()) &&
                    !first.capturedByOthers(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
        } else if (first instanceof PawnChessComponent) { //判断本方兵是否要吃对方的过路兵
            if (first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())){
                if (first.getCounter() == 1){ //斜走吃过路兵
                    if (temp != null) {
                        if (temp.getX() == chessComponent.getChessboardPoint().getX() && temp.getY() == chessComponent.getChessboardPoint().getY()) {
                            if (chessComponent.getChessColor() != chessboard.getCurrentColor()){
                                chessboard.removePasserPawn(first, chessComponent, chessboard); //将被吃的过路兵移除
                                return true;
                            } else return false;
                        } else return false;
                    } else return false;
                } else {
                    return chessComponent.getChessColor() != chessboard.getCurrentColor();
                }
            }
            else return false;
        } else {
            return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
        }
    }



    public boolean completelyCaptured(ChessColor rival, ChessComponent chessComponent){ //判断对手的王是否被将死
        ChessComponent kingOfRival = chessboard.getKingOfRival(chessboard.getCurrentColor());
        List<ChessboardPoint> canMovePoints = kingOfRival.getCanMovePoints(chessboard.getChessComponents(), rival);
        List<ChessComponent> chessComponentList = chessboard.getPlayerChessComponents(rival);
        int cnt = 0;
        if (canMovePoints.size() == 0){
            return false;
        }else {
            for (ChessboardPoint i : canMovePoints) {
                for (ChessComponent j : chessComponentList) {
                    if (kingOfRival.capturedByOthers(chessboard.getChessComponents(), i) && !j.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())) {
                        cnt++;
                    } else if (kingOfRival.capturedByPawn(chessboard.getChessComponents(), i) && !j.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())) {
                        cnt++;
                    }
                }
            }
        }
        return cnt == canMovePoints.size() * (chessComponentList.size() - 1);
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
                    a.append('n');
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

            a.append("\n");

        }
        QiJu.add(String.valueOf(a));
        if (cnt%2==0){
            a.append('B');
        }else {
            a.append('W');
        }
        a.append(b);
        return a.toString();
    }
    public static void rejiluQiJu(){
        if (QiJu.size()==0){
            JOptionPane.showMessageDialog(null,"已不能悔棋");
        }else {
            String shangyibu = QiJu.get(QiJu.size()-1);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessboard.reput(shangyibu.charAt(i*9+j),i,j,chessboard.getChessComponents());
                }
            }
            chessboard.swapColor();
            QiJu.remove(QiJu.size()-1);
            JOptionPane.showMessageDialog(null,"悔棋成功");
        }
    }
    public void forStart(){
        round = "Round Black";
        cnt=0;
        b=0;
    }

}
