package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BishopChessComponent extends ChessComponent {
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }




    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int dx = Math.abs(source.getX()-destination.getX());
        int dy = Math.abs(source.getY()-destination.getY());
        int row = source.getX();
        int col = source.getY();
        if (dy==dx){
            if (source.getX()>destination.getX()&&source.getY()>destination.getY()){
                for (int i = 1; i < source.getX()-destination.getX(); i++) {
                    if (!(chessComponents[row-i][col-i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            else if (source.getX()>destination.getX()&&source.getY()<destination.getY()){
                for (int i = 1; i < source.getX()-destination.getX(); i++) {
                    if (!(chessComponents[row-i][col+i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            else if (source.getX()<destination.getX()&&source.getY()>destination.getY()){
                for (int i = 1; i < destination.getX()-source.getX(); i++) {
                    if (!(chessComponents[row+i][col-i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            else if (source.getX()<destination.getX()&&source.getY()<destination.getY()){
                for (int i = 1; i < destination.getX()-source.getX(); i++) {
                    if (!(chessComponents[row+i][col+i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
        }
        else {return false;}
        return true;
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
        g.drawImage(bishopImage, 1, 0, getWidth() , getHeight(), this);
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
