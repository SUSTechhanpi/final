package view;

import model.ChessComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeListener implements ActionListener {
    private ChessGameFrame frame;
    int count=0;
    String[] arr=new String[]{"./images/MainBackground3.jpeg","./images/MainBackground.jpeg"};

    public ChangeListener(ChessGameFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getPanel().remove(frame.getLabel());
        frame.addBackground(arr[count]);
        frame.getPanel().repaint();
        count++;
        if(count>=arr.length) count=0;
        ChessComponent[][] cc = frame.getChessboard().getChessComponents();
        for(int i = 0; i < cc.length; i++) {
            for(int j =0; j< cc[i].length; j++) {
                cc[i][j].count++;
                if(cc[i][j].count > 2) cc[i][j].count = 0;
                cc[i][j].repaint();
            }
        }

    }
    public int getCount(){
        return count;
    }
}
