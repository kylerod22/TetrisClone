import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game {

    public static final int width = 10, height = 20;

    public final int blockFallDelayMillis = 1000;

    public static int[][] board;

    public Piece currPiece;

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

        currPiece = new Piece(Piece.pieceType.getRandomType());
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

        Piece.pieceType type = Piece.pieceType.getRandomType();
        while (runGame) {
            int currTime = (int) System.currentTimeMillis();
            if (currTime - initTime >= blockFallDelayMillis) {
                print();
                initTime = currTime;

                if (currPiece.atBottom()) {
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

    static public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("-----------------\n");
    }

    public static void main(String[] args) {
        new Game();
    }
}


