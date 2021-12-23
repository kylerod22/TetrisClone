import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Game {

    public static final int width = 10, height = 10;

    public final int blockFallDelayMillis = 1000;

    public static int[][] board;

    public Piece currPiece;

    public static String filled = "■";
    public static String empty = "□";

    public Game() {
        board = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
            }
        }

        JFrame f = new JFrame("Tetris");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400,400);
        f.setFocusable(true);

        Piece.pieceType type = Piece.pieceType.getRandomType();
        currPiece = new Piece(type);
        int initTime = (int) System.currentTimeMillis();

        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if(currPiece.canMove(-1,0)){
                            currPiece.move(-1,0);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:

                        if(currPiece.canMove(1,0)){
                            currPiece.move(1,0);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(currPiece.canMove(0,1)){
                            currPiece.move(0,1);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (currPiece.canRotate(-1)) {
                            currPiece.rotate(-1);
                        }
                        break;

                }
                if (!currPiece.atBottom()) print();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        boolean runGame = true;


        while (runGame) {
            int currTime = (int) System.currentTimeMillis();
            if (currTime - initTime >= blockFallDelayMillis) {
                print();
                initTime = currTime;

                if (currPiece.atBottom()) {

                    if (hasFilledRow()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateFilledRows();
                        //Update points here
                    }

                    type = Piece.pieceType.getRandomType();
                    runGame = Piece.canSpawnNewPiece(type);
                    if (runGame) {
                        currPiece = new Piece(type);
                    }
                    continue;
                }

                if (currPiece.canMove(0 , 1)) currPiece.move(0, 1);
            }

        }
    }

    public boolean hasFilledRow() {
        for (int i = 0; i < height; i++) {
            int[] row = board[i];
            if (IntStream.of(row).noneMatch(x -> x == 0)) {
                return true;
            }
        }
        return false;
    }

    public void updateFilledRows() {
        for (int i = 0; i < height; i++) {
            int[] row = board[i];
            if (IntStream.of(row).noneMatch(x -> x == 0)) {
                Arrays.fill(row, 0);
                for (int j = i; j >= 0; j--) {
                    if (j == 0) Arrays.fill(board[j], 0);
                    else {
                        for (int k = 0; k < width; k++) {
                            board[j][k] = board[j-1][k];
                        }
                    }
                    print();
                }
                i--;
            }
        }
    }

    static public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 0) {
                    System.out.print(empty + " ");
                } else {
                    System.out.print(filled + " ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("-----------------\n");
    }

    public static void main(String[] args) {
        new Game();
    }
}


