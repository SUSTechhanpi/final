package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FirstPage extends JFrame implements ActionListener{
    private final int WIDTH;
    private final int HEIGHT;
    private JPanel panel;
    private JLabel label;

    private GameController gameController;

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

//    private int screenWidth=Toolkit.getDefaultToolkit().getScreenSize().width;


    public FirstPage(int width, int height) {
        panel=new JPanel();
        panel.setLayout(null);
        panel.setBounds(0,0,width,height);
        this.setResizable(false);

        setTitle("国际象棋");
        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        BeginButton();
        addLoadButton();


        addBackground("./images/background4.gif");

        this.add(panel);

        panel.setVisible(true);
        setVisible(true);
    }

    private void BeginButton(){
        JButton beginButton = new JButton("Play");
        beginButton.setLocation(150,170);
        beginButton.setSize(200,60);
        beginButton.setFont(new Font("Blackadder ITC",Font.PLAIN,30));
        panel.add(beginButton);
        panel.repaint();

        beginButton.addActionListener(this);
    }

    private void addLoadButton(){
        JButton button = new JButton("Load");
        button.setBounds(150,270,200,60);
        button.setFont(new Font("Blackadder ITC", Font.PLAIN, 30));

        JFileChooser fileDialog = new JFileChooser();
        view.FirstPage gf = this;
        final File[] file = new File[1];
        boolean w = true;

//        if (file.toString().charAt(file.length-1)!='t'){
//            JOptionPane.showMessageDialog(null,"104","Error",1);
//            w =false;
//        }
//
        button.addActionListener(e -> {
            System.out.println("Click load");
            //            String path = JOptionPane.showInputDialog(this, "Input Path here");
            int returnVal = fileDialog.showOpenDialog(gf);
            //            if (path.charAt(path.length()-1)!='t') JOptionPane.showMessageDialog(null,"104","Error",1);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file[0] = fileDialog.getSelectedFile();
            }
            gameController.loadGameFromFile(file[0].getAbsolutePath());
        });

        panel.add(button);
        panel.repaint();
    }

    public void addBackground(String path){
        label=new JLabel();
        ImageIcon image=new ImageIcon(path);
        Image scaledImage=image.getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_DEFAULT);
        image.setImage(scaledImage);

        label.setBounds(0,0,this.getWidth(),this.getHeight());
        label.setLayout(null);
        label.setVisible(true);
        label.setIcon(image);
        panel.add(label);
        panel.repaint();
    }


    public static void main(String[] args) {
        FirstPage firstPage=new FirstPage(500,500);
        firstPage.setLocationRelativeTo(null);
        Music music = new Music(0);
        Thread bgmThread = new Thread(music);
        bgmThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });
    }
}

