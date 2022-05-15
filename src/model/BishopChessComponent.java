package model;


        import controller.ClickController;
        import view.ChessboardPoint;

        import javax.imageio.ImageIO;
        import java.awt.*;
        import java.io.File;
        import java.io.IOException;

public class BishopChessComponent extends ChessComponent{

    private Image Bishop_WHITE;
    private Image Bishop_BLACK;

    private Image bishopImage;

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        initiateQueenImage(chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint source = getChessboardPoint();
        if ((source.getX() + source.getY()) == (destination.getX() + destination.getY())) {
            int col = Math.min(source.getY(), destination.getY()) + 1;
            int row = Math.max(source.getX(), destination.getX()) - 1;
            while(col < Math.max(source.getY(), destination.getY()) && col >= 0 && row > Math.min(source.getX(), destination.getX()) && row <= 7){
                if(!(chessComponents[row][col] instanceof EmptySlotComponent)){
                    return false;
                }
                col++;
                row--;
            }
        } else if ((source.getX() - source.getY()) == (destination.getX() - destination.getY())) {
            int col = Math.min(source.getY(), destination.getY()) + 1;
            int row = Math.min(source.getX(), destination.getX()) + 1;
            while(col < Math.max(source.getY(), destination.getY()) && row < Math.max(source.getX(), destination.getX())){
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
                col++;
                row++;
            }
        }else {
            return false;
        }
        return true;
    }


    @Override
    public void loadResource() throws IOException {
        if (Bishop_WHITE == null) {
            Bishop_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (Bishop_BLACK == null) {
            Bishop_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = Bishop_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = Bishop_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}