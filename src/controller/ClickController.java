package controller;


import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import model.PawnChessComponent;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;

import java.util.List;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

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
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                ChessComponent temp = chessComponent;
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                //判断行棋方是否移动的是兵且是否到底线 若到底线则升变
                if (first instanceof PawnChessComponent){
                    if (arriveBaseline(first)){
                        chessboard.promotePawn(ChessGameFrame.pawnPromotion(), first);
                    }
                }
                //判断当前行棋方移动后是否被对方将军 若被将军 则不能移动该棋子并提示
                ChessColor rival = (chessboard.getCurrentColor() == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
                if (chessboard.captureKing(rival)){
                    ChessGameFrame.promptOfBeingCaptured();
                    chessboard.swapChessComponents(first, chessComponent);
                    //chessboard.swapChessComponents(temp, chessComponent);
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
        if (first instanceof KingChessComponent){
            return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint()) &&
                    !first.capturedByPawn(chessboard.getChessComponents(), chessComponent.getChessboardPoint()) &&
                    !first.capturedByOthers(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
        }else {
            return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
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
