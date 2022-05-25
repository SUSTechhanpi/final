package controller;

import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    public void initialGame(){
        chessboard.initialGame();
    };
    private Chessboard chessboard;

    public void savecessboard(){
        chessboard.saveBoard();
    }

    public void regret(){
        chessboard.clickregret();
    }
    public int getcolor(){
        int w=chessboard.getWhiteWin();
        return w;
    }

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
