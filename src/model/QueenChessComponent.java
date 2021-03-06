package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class QueenChessComponent extends ChessComponent {
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }




    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int dx = Math.abs(source.getX()-destination.getX());
        int dy = Math.abs(source.getY()-destination.getY());
        if (dy==dx){
            int row = source.getX();
            int col = source.getY();
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
        else if (source.getX() == destination.getX()) {
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
        } else { // Not on the same row or the same column.
            return false;
        }
        return true;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param g ?????????????????????
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 1, 0, getWidth() , getHeight(), this);
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
