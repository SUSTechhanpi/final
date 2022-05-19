package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPage extends JFrame implements ActionListener{
    private final int WIDTH;
    private final int HEIGHT;
    private JPanel panel;
    private JLabel label;

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
        ChangeBackground();


        addBackground("./images/background.png");

        this.add(panel);

        panel.setVisible(true);
        setVisible(true);
    }

    private void BeginButton(){
        JButton beginButton = new JButton("开始游戏！");
        beginButton.setLocation(150,150);
        beginButton.setSize(200,60);
        beginButton.setFont(new Font("楷体",Font.BOLD,20));
        panel.add(beginButton);
        panel.repaint();

        beginButton.addActionListener(this);
    }

    private void ChangeBackground(){
        JButton changeBackground = new JButton("更改背景");
        changeBackground.setBounds(150,250,200,60);
        changeBackground.setFont(new Font("楷体",Font.BOLD,20));

        changeBackground.addActionListener(new ChangeListener(this));



        changeBackground.setVisible(true);
        panel.add(changeBackground);
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
        new FirstPage(500,500);
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

