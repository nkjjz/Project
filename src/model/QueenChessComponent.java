package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent{

    private Image Queen_WHITE;
    private Image Queen_BLACK;

    private Image queenImage;


    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        initiateQueenImage(chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if ((source.getX() + source.getY()) == (destination.getX() + destination.getY())) {
            int col = Math.min(source.getY(), destination.getY()) + 1;
            int row = Math.max(source.getX(), destination.getX()) - 1;
            while(col < Math.max(source.getY(), destination.getY()) && row > Math.min(source.getX(), destination.getX())){
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
        if (Queen_WHITE == null) {
            Queen_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (Queen_BLACK == null) {
            Queen_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = Queen_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = Queen_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
