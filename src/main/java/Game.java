import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game {

    public static final int width = 10, height = 10;

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



        currPiece = new Piece();
        print();


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
                            print();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:

                        if(currPiece.canMove(1,0)){
                            currPiece.move(1,0);
                            print();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(currPiece.canMove(0,1)){
                            currPiece.move(0,1);
                            print();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (currPiece.canRotate(-1)) {
                            currPiece.rotate(-1);
                            print();
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });



        /*Piece piece2 = new Piece(Piece.pieceType.T);

        while (piece2.canMove(0 , 1)) {
            piece2.move(0, 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print();
        } */

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


