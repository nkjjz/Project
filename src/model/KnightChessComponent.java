
        package model;

        import controller.ClickController;
        import view.ChessboardPoint;

        import javax.imageio.ImageIO;
        import java.awt.*;
        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        public class KnightChessComponent extends ChessComponent{

    private Image Knight_WHITE;
    private Image Knight_BLACK;

    private Image knightImage;
    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        initiateKnightImage(chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint source = getChessboardPoint();
        if(Math.abs(source.getX() - destination.getX()) == 1 && Math.abs(source.getY() - destination.getY()) == 2){
            return true;
        }else if(Math.abs(source.getX() - destination.getX()) == 2 && Math.abs(source.getY() - destination.getY()) == 1){
            return true;
        }else {
            return false;
        }
    }

    public java.util.List<ChessboardPoint> getCanMovePoints(ChessComponent[][] chessComponents, ChessColor player) {
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

    @Override
    public void loadResource() throws IOException {
        if (Knight_WHITE == null) {
            Knight_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (Knight_BLACK == null) {
            Knight_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }
    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = Knight_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = Knight_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}