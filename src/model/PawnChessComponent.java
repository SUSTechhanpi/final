package model;


import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class PawnChessComponent extends ChessComponent {
    private static Image Pawn_WHITE;
    private static Image Pawn_BLACK;

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (Pawn_WHITE == null) {
            Pawn_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (Pawn_BLACK == null) {
            Pawn_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
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

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int dx = destination.getX()-source.getX();
        int dy = destination.getY()-source.getY();
        if (chessComponents[source.getX()][source.getY()].chessColor==ChessColor.BLACK){
            if (dx==1&&Math.abs(dy)==1){
                if (chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.WHITE){
                    return true;
                }
                else if (chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.WHITE){
                    return true;
                }
            }
            if (source.getX()==1){
                if (dx==1&&dy==0){
                    if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                        return true;
                    }
                }
                else if (dx==2&&dy==0){
                    if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                        if (chessComponents[source.getX()+1][source.getY()] instanceof EmptySlotComponent){
                            return true;
                        }
                    }
                }
            }
            else {
                if (dx==1&&dy==0){
                    if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                        return true;
                    }
                }
            }
        }
        else if (chessComponents[source.getX()][source.getY()].chessColor==ChessColor.WHITE) {
            if (dx==-1&&Math.abs(dy)==1){
                if (chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.BLACK){
                    return true;
                }
                else if (chessComponents[destination.getX()][destination.getY()].getChessColor()==ChessColor.BLACK){
                    return true;
                }
            }
            if (source.getX()==6){
                if (dx==-1&&dy==0){
                    if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                        return true;
                    }
                }
                else if (dx==-2&&dy==0){
                    if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                        if (chessComponents[source.getX()-1][source.getY()] instanceof EmptySlotComponent){
                            return true;
                        }
                    }
                }
            }
            else {
                if (dx==-1&&dy==0){
                    if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                        return true;
                    }
                }
            }
        }
        else {return false;}
        return false;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 1, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    @Override
    public List<ChessboardPoint> canMoveTo() {
        return null;
    }
}
