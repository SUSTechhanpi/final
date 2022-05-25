package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private static final Color[] BACKGROUND_COLORS = {Color.WHITE, Color.BLACK, Color.PINK, Color.CYAN};
    JLabel statusLabel;
    private JLabel label;
    private JPanel panel;
    private int n;
    private Chessboard chessboard;

    public Chessboard getChessboard() {
        return chessboard;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }


    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public ChessGameFrame(int width, int height) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, width, height);
        this.setResizable(false);

        setTitle("2022 CS102A Project "); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addLabel();
        addChessboard();
        addRestartButton();
        addLoadButton();
        addSaveButton();
        addRegretButton();
        ChangeBackground();
        addUserButton();

        addBackground("./images/MainBackground3.jpeg");

        this.add(panel);

        panel.setVisible(true);
        setVisible(true);
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setStatuslabel(statusLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        panel.add(chessboard);
        panel.repaint();
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("Hello Teacher");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Blackadder ITC", Font.BOLD, 20));
        panel.add(statusLabel);
        panel.repaint();
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) ->{
            JOptionPane.showMessageDialog(this, "Has Restarted the ChessBoard");
            gameController.initialGame();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("Blackadder ITC", Font.BOLD, 30));
        panel.add(button);
        panel.repaint();

    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Blackadder ITC", Font.BOLD, 30));
        panel.add(button);
        panel.repaint();

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (path.charAt(path.length()-1)!='t') JOptionPane.showMessageDialog(null,"104","Error",1);
            gameController.loadGameFromFile(path);
        });
    }



    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.addActionListener((e) ->{
            gameController.savecessboard();
            JOptionPane.showMessageDialog(this, "Has Saved the ChessBoard");
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 380);
        button.setSize(200, 60);
        button.setFont(new Font("Blackadder ITC", Font.BOLD, 30));
        panel.add(button);
        panel.repaint();

//        JButton button = new JButton("Save");
//        JFileChooser fileDialog = new JFileChooser();
//        view.ChessGameFrame gf = this;
//        final File[] file = new File[1];
//        button.addActionListener(e -> {
//            System.out.println("Click load");
////            String path = JOptionPane.showInputDialog(this, "Input Path here");
//            int returnVal = fileDialog.showOpenDialog(gf);
////            if (path.charAt(path.length()-1)!='t') JOptionPane.showMessageDialog(null,"104","Error",1);
//            if (returnVal == JFileChooser.APPROVE_OPTION) {
//                file[0] = fileDialog.getSelectedFile();
//            }
//            gameController.savecessboard(file[0].getAbsolutePath());
//        });
//        button.setLocation(HEIGTH, HEIGTH / 10 + 380);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        panel.add(button);
//        panel.repaint();
    }

    private void addRegretButton() {
        JButton button = new JButton("Regret");
        String path = "c:\\project\\resource\\history.txt";
        File file=new File(path);
        button.addActionListener((e) ->{
            JOptionPane.showMessageDialog(this, "Has Regreted the ChessBoard");
            gameController.regret();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 580);
        button.setSize(200, 60);
        button.setFont(new Font("Blackadder ITC", Font.BOLD, 30));
        panel.add(button);
        panel.repaint();

    }

    private void addUserButton() {
        JButton button = new JButton("property");
        button.addActionListener((e) -> {
            int i = gameController.getcolor();
            JOptionPane.showMessageDialog(this, "白方等级为：Rank "+i);
            gameController.initialGame();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 80);
        button.setSize(200, 60);
        button.setFont(new Font("Blackadder ITC", Font.BOLD, 30));
        panel.add(button);
        panel.repaint();

    }
    void addBackground(String path) {
        label = new JLabel();
        ImageIcon image = new ImageIcon(path);
        Image scaledImage = image.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
        image.setImage(scaledImage);
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        label.setLayout(null);
        label.setVisible(true);
        label.setIcon(image);
        panel.add(label);
        panel.repaint();
    }

    private void ChangeBackground() {
        JButton changeBackground = new JButton("更改背景");
        changeBackground.setLocation(HEIGTH, HEIGTH / 10 + 280);
        changeBackground.setSize(200, 60);
        changeBackground.setFont(new Font("华文行楷", Font.BOLD, 20));
        ChangeListener changeListener = new ChangeListener(this);
        changeBackground.addActionListener(changeListener);
        n = changeListener.getCount();


        changeBackground.setVisible(true);
        panel.add(changeBackground);
        panel.repaint();
    }

    public int getN() {
        return n;
    }
}
