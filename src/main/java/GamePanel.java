import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class GamePanel extends JPanel implements Runnable {
    private final int pixelSize = 32;
    private final int borderRadius = 1;
    private final int screenWidth = pixelSize * Game.WIDTH + 2 * pixelSize * borderRadius;
    private final int screenHeight = pixelSize * Game.HEIGHT + 2 * pixelSize * borderRadius;
    public static String filled = "■";
    public static String empty = "□";

    KeyHandler keyHandler = new KeyHandler();

    Thread gameThread;

    public final int blockFallDelayMillis = 1000, moveDelayMillis = 100;

    public static int[][] board;

    final Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};

    public Piece currPiece;

    private static int points = 0;
    private final int tetrisBasePoints = 100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        int initFallTime = (int) System.currentTimeMillis(), initMoveTime = (int) System.currentTimeMillis();
        board = new int[Game.HEIGHT][Game.WIDTH];
        Piece.pieceType type = Piece.pieceType.getRandomType();
        currPiece = new Piece(type);


        boolean runGame = true;
        while (runGame) {
            repaint();
            if ((int) System.currentTimeMillis() - initMoveTime >= moveDelayMillis) {
                initMoveTime = (int) System.currentTimeMillis();
                update();
            }
            if ((int) System.currentTimeMillis() - initFallTime >= blockFallDelayMillis) {
                initFallTime = (int) System.currentTimeMillis();

                if (currPiece.atBottom()) {
                    if (hasFilledRow()) {
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateFilledRows();
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

    public void update() {
        if (keyHandler.leftPressed) {
            if (currPiece.canMove(-1, 0)) currPiece.move(-1, 0);
        }
        if (keyHandler.rightPressed) {
            if (currPiece.canMove(1, 0)) currPiece.move(1, 0);
        }
        if (keyHandler.downPressed) {
            if (currPiece.canMove(0, 1)) currPiece.move(0, 1);
        }
        if (keyHandler.upPressed) {
            if (currPiece.canRotate(-1)) currPiece.rotate(-1);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (board == null) return;

        drawBorder(g2);

        for (int i = 0; i < Game.HEIGHT; i++) {
            for (int j = 0; j < Game.WIDTH; j++) {
                if (board[i][j] >= 1) {
                    g2.setColor(colors[board[i][j] - 1]);
                    g2.fillRect(j * pixelSize + borderRadius * pixelSize, i * pixelSize + borderRadius * pixelSize, pixelSize, pixelSize);
                }
            }
        }
        g2.dispose();
    }

    public void drawBorder (Graphics2D g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, screenWidth, pixelSize * borderRadius);
        g.fillRect(0, screenHeight - borderRadius * pixelSize, screenWidth, pixelSize * borderRadius);
        g.fillRect(0,0, pixelSize * borderRadius, screenHeight);
        g.fillRect(screenWidth - borderRadius * pixelSize, 0, pixelSize * borderRadius, screenHeight);
    }

    public boolean hasFilledRow() {
        for (int i = 0; i < Game.HEIGHT; i++) {
            int[] row = board[i];
            if (IntStream.of(row).noneMatch(x -> x == 0)) {
                return true;
            }
        }
        return false;
    }

    public void updateFilledRows() {
        int clearedRows = 0;
        for (int i = 0; i < Game.HEIGHT; i++) {
            int[] row = board[i];
            if (IntStream.of(row).noneMatch(x -> x == 0)) {
                clearedRows++;
                Arrays.fill(row, 0);
                for (int j = i; j >= 0; j--) {
                    if (j == 0) Arrays.fill(board[j], 0);
                    else {
                        System.arraycopy(board[j - 1], 0, board[j], 0, Game.WIDTH);
                    }
                }
                i--;
            }
        }
        points += tetrisBasePoints * Math.pow(2, clearedRows - 1);
    }
}
