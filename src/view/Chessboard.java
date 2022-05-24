package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    Stack<String> stack = new Stack<>();

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private List<String> history = new ArrayList<>();

    private JLabel statuslabel;

    public void setStatuslabel(JLabel statuslabel) {
        this.statuslabel = statuslabel;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKingOnBoard(0,3,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1,3,ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initQueenOnBoard(0, 4, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 4, ChessColor.WHITE);
        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
        }
        initialGame();
    }


    public void initialGame(){
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1,3,ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 4, ChessColor.WHITE);
        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
        }

        currentColor=ChessColor.WHITE;
        repaint();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        regret();

        whoWin();
        chess1.repaint();
        chess2.repaint();
    }

    public void whoWin(){
        int king=0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent){
                    king++;
                }
            }
        }
        if (king!=2){
            initialGame();
        }
//加标签
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        statuslabel.setText(currentColor.toString());
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            if (chessData.get(i).length()!=8) errorBoard101();
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessData.get(i).charAt(j)=='K'){
                    initKingOnBoard(i,j,ChessColor.BLACK);
                }
                else if (chessData.get(i).charAt(j)=='k'){
                    initKingOnBoard(i,j,ChessColor.WHITE);
                }
                else if (chessData.get(i).charAt(j)=='Q'){
                    initQueenOnBoard(i,j,ChessColor.BLACK);
                }
                else if (chessData.get(i).charAt(j)=='q'){
                    initQueenOnBoard(i,j,ChessColor.WHITE);
                }
                else if (chessData.get(i).charAt(j)=='B'){
                    initBishopOnBoard(i,j,ChessColor.BLACK);
                }
                else if (chessData.get(i).charAt(j)=='b'){
                    initBishopOnBoard(i,j,ChessColor.WHITE);
                }
                else if (chessData.get(i).charAt(j)=='N'){
                    initKnightOnBoard(i,j,ChessColor.BLACK);
                }
                else if (chessData.get(i).charAt(j)=='n'){
                    initKnightOnBoard(i,j,ChessColor.WHITE);
                }
                else if (chessData.get(i).charAt(j)=='R'){
                    initRookOnBoard(i,j,ChessColor.BLACK);
                }
                else if (chessData.get(i).charAt(j)=='r'){
                    initRookOnBoard(i,j,ChessColor.WHITE);
                }
                else if (chessData.get(i).charAt(j)=='P'){
                    initPawnOnBoard(i,j,ChessColor.BLACK);
                }
                else if (chessData.get(i).charAt(j)=='p'){
                    initPawnOnBoard(i,j,ChessColor.WHITE);
                }
                else {
                    errorBoard101();
                }
            }
        }
        if (chessData.get(8).charAt(0)=='w'){
            currentColor=ChessColor.WHITE;
        }
        else if(chessData.get(8).charAt(0)=='b'){
            currentColor=ChessColor.BLACK;
        }
        statuslabel.setText(currentColor.toString());
        repaint();
    }



    public void errorBoard101(){
        //弹出一个弹窗告诉错误并重新load
    }

    public void saveBoard(){
        String nowChessBoard = new String();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    if (chessComponents[i][j] instanceof KingChessComponent){
                        nowChessBoard += "k";
                    }
                    else if (chessComponents[i][j] instanceof QueenChessComponent){
                        nowChessBoard += "q";
                    }
                    else if (chessComponents[i][j] instanceof BishopChessComponent){
                        nowChessBoard += "b";
                    }
                    else if (chessComponents[i][j] instanceof RookChessComponent){
                        nowChessBoard += "r";
                    }
                    else if (chessComponents[i][j] instanceof KnightChessComponent){
                        nowChessBoard += "n";
                    }
                    else if (chessComponents[i][j] instanceof PawnChessComponent){
                        nowChessBoard += "p";
                    }
                }
                else if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    if (chessComponents[i][j] instanceof KingChessComponent){
                        nowChessBoard += "K";
                    }
                    else if (chessComponents[i][j] instanceof QueenChessComponent){
                        nowChessBoard += "Q";
                    }
                    else if (chessComponents[i][j] instanceof BishopChessComponent){
                        nowChessBoard += "B";
                    }
                    else if (chessComponents[i][j] instanceof RookChessComponent){
                        nowChessBoard += "R";
                    }
                    else if (chessComponents[i][j] instanceof KnightChessComponent){
                        nowChessBoard += "N";
                    }
                    else if (chessComponents[i][j] instanceof PawnChessComponent){
                        nowChessBoard += "P";
                    }
                }
                else {
                    nowChessBoard +="0";
                }
            }
            nowChessBoard +="\n";
        }
        if (currentColor==ChessColor.BLACK){
            nowChessBoard+="b";
        }
        else if (currentColor==ChessColor.WHITE){
            nowChessBoard+="w";
        }
        nowChessBoard +="\n";
        setupTXT(nowChessBoard);
    }

    public void setupTXT(String a){
        String path = "c:\\project\\resource\\save.txt";
        File file=new File(path);
        if (!file.exists()){
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void regret(){
        String nowChessBoard = new String();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    if (chessComponents[i][j] instanceof KingChessComponent){
                        nowChessBoard += "k";
                    }
                    else if (chessComponents[i][j] instanceof QueenChessComponent){
                        nowChessBoard += "q";
                    }
                    else if (chessComponents[i][j] instanceof BishopChessComponent){
                        nowChessBoard += "b";
                    }
                    else if (chessComponents[i][j] instanceof RookChessComponent){
                        nowChessBoard += "r";
                    }
                    else if (chessComponents[i][j] instanceof KnightChessComponent){
                        nowChessBoard += "n";
                    }
                    else if (chessComponents[i][j] instanceof PawnChessComponent){
                        nowChessBoard += "p";
                    }
                }
                else if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    if (chessComponents[i][j] instanceof KingChessComponent){
                        nowChessBoard += "K";
                    }
                    else if (chessComponents[i][j] instanceof QueenChessComponent){
                        nowChessBoard += "Q";
                    }
                    else if (chessComponents[i][j] instanceof BishopChessComponent){
                        nowChessBoard += "B";
                    }
                    else if (chessComponents[i][j] instanceof RookChessComponent){
                        nowChessBoard += "R";
                    }
                    else if (chessComponents[i][j] instanceof KnightChessComponent){
                        nowChessBoard += "N";
                    }
                    else if (chessComponents[i][j] instanceof PawnChessComponent){
                        nowChessBoard += "P";
                    }
                }
                else {
                    nowChessBoard +="0";
                }
            }
        }
        if (currentColor==ChessColor.BLACK){
            nowChessBoard+="w";
        }
        else if (currentColor==ChessColor.WHITE){
            nowChessBoard+="b";
        }
        stack.push(nowChessBoard);
    }

    public void clickregret(){
        stack.pop();
        List<String> board=new ArrayList<>();
        String regretboard=stack.peek();
        for (int i = 0; i < 8; i++) {
            String k = "";
            for (int j = 0; j < 8; j++) {
                k += regretboard.charAt(i*8+j);
            }
            board.add(k);
        }
        board.add(String.valueOf(regretboard.charAt(64)));
        loadGame(board);

    }
}
