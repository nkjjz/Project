package controller;


import model.ChessComponent;
import model.KingChessComponent;
import view.Chessboard;
import view.ChessboardPoint;

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
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                //判断是否将军
                first.captureKing(chessboard.getChessComponents(), chessboard.getKingOfRival());
                chessboard.swapColor();// 更换行棋方
                first.setSelected(false);
                first = null;
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
                    !((KingChessComponent) first).capturedByPawn(chessboard.getChessComponents(), chessComponent.getChessboardPoint()) &&
                    !((KingChessComponent) first).capturedByOthers(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
        }else {
            return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
        }
    }
}
