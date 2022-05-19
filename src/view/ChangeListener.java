package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeListener implements ActionListener {
    private FirstPage frame;
    int count=0;
    String[] arr=new String[]{"./images/background3.gif","./images/background4.gif","./images/background2.png","./images/background.png"};

    public ChangeListener(FirstPage frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getPanel().remove(frame.getLabel());
        frame.addBackground(arr[count]);
        frame.getPanel().repaint();
        count++;
        if(count>=arr.length) count=0;
    }
}
