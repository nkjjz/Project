package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
                switch (getChessColor()) {
                    case BLACK:
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
                        } else {
                            return destination.getX() - source.getX() == 1;
                        }
                        break;
                    case WHITE:
                        if (source.getX() == 6) {
                            if (source.getX() - destination.getX() <= 2 && source.getX() - destination.getX() > 0) {
                                int col = source.getY();
                                for (int row = source.getX() - 1; row > destination.getX(); row--) {
                                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                                        return false;
                                    }
                                }
                            }else {
                                return false;
                            }
                        } else {
                            return source.getX() - destination.getX() == 1;
                        }
                        break;
                }
            }else { //斜走吃子
                switch (getChessColor()){
                    case BLACK :
                        if (destination.getX() - source.getX() == 1 && Math.abs(destination.getY() - source.getY()) == 1) {
                            return !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
                        }else {
                            return false;
                        }
                    case WHITE :
                        if (source.getX() - destination.getX() == 1 && Math.abs(destination.getY() - source.getY()) == 1) {
                            return !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
                        }else {
                            return false;
                        }
                }
            }
            return true;
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

