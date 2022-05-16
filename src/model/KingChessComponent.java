
        package model;

        import controller.ClickController;
        import view.ChessboardPoint;

        import javax.imageio.ImageIO;
        import java.awt.*;
        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        public class KingChessComponent extends ChessComponent{

    private Image King_WHITE;
    private Image King_BLACK;

    private Image kingImage;



    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        initiateKingImage(chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){ //王不能走到被将军的位置
        ChessboardPoint source = getChessboardPoint();
        int distanceOfCol = Math.abs(source.getY() - destination.getY());
        int distanceOfRow = Math.abs(source.getX() - destination.getX());
        return distanceOfCol <= 1 && distanceOfRow <= 1;
    }

    public java.util.List<ChessboardPoint> getCanMovePoints(ChessComponent[][] chessComponents, ChessColor player){ //仅得到王在理论上可移动的点（不考虑移动后被将军）
        List<ChessboardPoint> canMovePoints = new ArrayList<>();
        int row = this.getChessboardPoint().getX();
        int col = this.getChessboardPoint().getY();
        if (row - 1 >= 0){
            if (chessComponents[row - 1][col].getChessColor() != player) {
                canMovePoints.add(new ChessboardPoint(row - 1, col));
            }
            if (col - 1 >= 0){
                if (chessComponents[row - 1][col - 1].getChessColor() != player){
                    canMovePoints.add(new ChessboardPoint(row - 1, col - 1));
                }
                if (chessComponents[row][col - 1].getChessColor() != player) {
                    canMovePoints.add(new ChessboardPoint(row, col - 1));
                }
            }
            if (col + 1 < 8){
                if (chessComponents[row - 1][col + 1].getChessColor() != player) {
                    canMovePoints.add(new ChessboardPoint(row - 1, col + 1));
                }
                if (chessComponents[row][col + 1].getChessColor() != player) {
                    canMovePoints.add(new ChessboardPoint(row, col + 1));
                }
            }
        }
        if (row + 1 < 8){
            if (chessComponents[row + 1][col].getChessColor() != player) {
                canMovePoints.add(new ChessboardPoint(row + 1, col));
            }
            if (col - 1 >= 0) {
                if (chessComponents[row + 1][col - 1].getChessColor() != player) {
                    canMovePoints.add(new ChessboardPoint(row + 1, col - 1));
                }
            }
            if (col + 1 < 8){
                if (chessComponents[row + 1][col + 1].getChessColor() != player) {
                    canMovePoints.add(new ChessboardPoint(row + 1, col + 1));
                }
            }
        }
        return canMovePoints;
    }

    public boolean capturedByPawn(ChessComponent[][] chessComponent, ChessboardPoint destination){ //判断王斜走后是否会被兵将军
        int x = destination.getX();
        int y = destination.getY();
        if (this.chessColor != ChessColor.NONE) {
            if (this.chessColor == ChessColor.BLACK) {
                if (x + 1 <= 6) {
                    if (y == 7) {
                        return chessComponent[x + 1][y - 1].chessColor == ChessColor.WHITE && chessComponent[x + 1][y - 1] instanceof PawnChessComponent;
                    } else if (y == 0) {
                        return chessComponent[x + 1][y + 1].chessColor == ChessColor.WHITE && chessComponent[x + 1][y + 1] instanceof PawnChessComponent;
                    } else {
                        return (chessComponent[x + 1][y + 1].chessColor == ChessColor.WHITE && chessComponent[x + 1][y + 1] instanceof PawnChessComponent) ||
                                (chessComponent[x + 1][y - 1].chessColor == ChessColor.WHITE && chessComponent[x + 1][y - 1] instanceof PawnChessComponent);
                    }
                } else {
                    return false;
                }
            } else {
                if (x - 1 >= 1) {
                    if (y == 7) {
                        return chessComponent[x - 1][y - 1].chessColor == ChessColor.BLACK && chessComponent[x - 1][y - 1] instanceof PawnChessComponent;
                    } else if (y == 0) {
                        return chessComponent[x - 1][y + 1].chessColor == ChessColor.BLACK && chessComponent[x - 1][y + 1] instanceof PawnChessComponent;
                    } else {
                        return (chessComponent[x - 1][y + 1].chessColor == ChessColor.BLACK && chessComponent[x - 1][y + 1] instanceof PawnChessComponent) ||
                                (chessComponent[x - 1][y - 1].chessColor == ChessColor.BLACK && chessComponent[x - 1][y - 1] instanceof PawnChessComponent);
                    }
                } else {
                    return false;
                }
            }
        }else {
            return false;
        }
    }

    public boolean capturedByOthers(ChessComponent[][] chessComponents, ChessboardPoint destination){ //判断王移动后是否会被其他子将军
        ArrayList<ChessComponent> rival = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (!(chessComponents[i][j] instanceof EmptySlotComponent)){
                    if (chessComponents[i][j].chessColor != this.chessColor){
                        rival.add(chessComponents[i][j]);
                    }
                }
            }
        }
        for (ChessComponent i : rival){
            if (!(i instanceof PawnChessComponent)) {
                if (i.canMoveTo(chessComponents, destination)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        if (King_WHITE == null) {
            King_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (King_BLACK == null) {
            King_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = King_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = King_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}