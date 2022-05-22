
        package model;

        import controller.ClickController;
        import view.ChessboardPoint;

        import javax.imageio.ImageIO;
        import java.awt.*;
        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        public class PawnChessComponent extends ChessComponent{

    private Image Pawn_WHITE;
    private Image Pawn_BLACK;

    private Image pawnImage;


    public void loadResource() throws IOException {
        if (Pawn_WHITE == null) {
            Pawn_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (Pawn_BLACK == null) {
            Pawn_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        initiatePawnImage(chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getY() == destination.getY()) { //直走
            if (chessColor == ChessColor.BLACK) {
                if (source.getX() == 1) { //是否第一次移动
                    if (destination.getX() - source.getX() <= 2 && destination.getX() - source.getX() > 0) {
                        int col = source.getY();
                        for (int row = source.getX() + 1; row < destination.getX(); row++) {
                            if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                    }else {
                        return false;
                    }
                }else {
                    if (destination.getX() - source.getX() == 1){
                        this.setCounter(0);
                        return chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent;
                    }else {
                        this.setCounter(0);
                        return false;
                    }
                }
            }else {
                if (source.getX() == 6) {
                    if (source.getX() - destination.getX() <= 2 && source.getX() - destination.getX() > 0) {
                        int col = source.getY();
                        for (int row = source.getX() - 1; row > destination.getX(); row--) {
                            if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (source.getX() - destination.getX() == 1){
                        this.setCounter(0);
                        return chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent;
                    }else {
                        this.setCounter(0);
                        return false;
                    }
                }
            }
        }else { //斜走吃子或斜走吃过路兵
            if (chessColor == ChessColor.BLACK) {
                if (destination.getX() - source.getX() == 1 && Math.abs(destination.getY() - source.getY()) == 1) {
                    if ((chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.WHITE)){
                        this.setCounter(0);
                        return true;
                    }else if ((chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE)){
                        this.setCounter(1);
                        return true;
                    }
                }else return false;
            }else {
                if (source.getX() - destination.getX() == 1 && Math.abs(destination.getY() - source.getY()) == 1) {
                    if (chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.BLACK){
                        this.setCounter(0);
                        return true;
                    } else if (chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE){
                        this.setCounter(1);
                        return true;
                    }
                }else return false;
            }
        }
        this.setCounter(0);
        return true;
    }

    @Override
    public List<ChessboardPoint> getCanMovePoints(ChessComponent[][] chessComponents, ChessColor player) {
        List<ChessboardPoint> canMovePoints = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (canMoveTo(chessComponents, point)){
                    canMovePoints.add(point);
                }
            }
        }
        return canMovePoints;
    }

    public ChessboardPoint haveAdjacentPawn(ChessComponent[][] chessboard){ //分别判断黑白方是否形成过路兵的情况
int col = this.getChessboardPoint().getY();
switch (this.getChessColor()){
    case BLACK:
        if (this.getChessboardPoint().getX() == 3){
            if (col - 1 >= 0 && col + 1 <= 7) {
                if (chessboard[3][col - 1] instanceof PawnChessComponent && chessboard[3][col - 1].getChessColor() != this.getChessColor()) {
                    return new ChessboardPoint(2, col);
                } else if (chessboard[3][col + 1] instanceof PawnChessComponent && chessboard[3][col + 1].getChessColor() != this.getChessColor()) {
                    return new ChessboardPoint(2, col);
                }
            }else if (col == 0){
                if (chessboard[3][col + 1] instanceof PawnChessComponent && chessboard[3][col + 1].getChessColor() != this.getChessColor()){
                    return new ChessboardPoint(2, 0);
                }
            }else if (col == 7){
                if (chessboard[3][col - 1] instanceof PawnChessComponent && chessboard[3][col - 1].getChessColor() != this.getChessColor()){
                    return new ChessboardPoint(2, 7);
                }
            }
        }else {
            return null;
        }
        break;
    case WHITE:
        if (this.getChessboardPoint().getX() == 4){
            if (col - 1 >= 0 && col + 1 <= 7) {
                if (chessboard[4][col - 1] instanceof PawnChessComponent && chessboard[3][col - 1].getChessColor() != this.getChessColor()) {
                    return new ChessboardPoint(5, col);
                } else if (chessboard[4][col + 1] instanceof PawnChessComponent && chessboard[3][col + 1].getChessColor() != this.getChessColor()) {
                    return new ChessboardPoint(5, col);
                }
            }else if (col == 0){
                if (chessboard[4][col + 1] instanceof PawnChessComponent && chessboard[3][col + 1].getChessColor() != this.getChessColor()){
                    return new ChessboardPoint(5, 0);
                }
            }else if (col == 7){
                if (chessboard[4][col - 1] instanceof PawnChessComponent && chessboard[3][col - 1].getChessColor() != this.getChessColor()){
                    return new ChessboardPoint(5, 7);
                }
            }
        }else {
            return null;
        }
        break;
}
return null;
}



    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = Pawn_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = Pawn_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
