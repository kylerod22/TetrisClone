import java.util.ArrayList;
import java.util.Random;

public class Piece {

    public int colPos, rowPos;
    public static ArrayList<Block> blockList;
    public static ArrayList<int[]> localCoords;
    private pieceType type;


    public enum pieceType {
        L,
        BACK_L,
        S,
        BACK_S,
        T,
        LINE,
        SQUARE;

        public static pieceType getRandomType() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public boolean canMove(int dx, int dy) {
        for (Block b : blockList) {
            if (!b.canMove(dx, dy)) {
                return false;
            }
        }
        return true;
    }

    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            rowPos += dy;
            colPos += dx;
            if (canMove(dx, dy)) {
                for (Block b : blockList) {
                    b.move(dx, dy);
                }
            }
        }
    }

    public boolean canRotate(int dir) {
        if (type == pieceType.SQUARE) return true; //Squares rotated are the same
        for (Block b : blockList) {
            if (!b.canRotate(dir, colPos, rowPos)) {
                return false;
            }
        }
        return true;
    }

    public void rotate(int dir) {
        if (type == pieceType.SQUARE) return; //Squares rotated are the same
        if (dir == 1 || dir == -1) {
            if (canRotate(dir)) {
                for (Block b : blockList) {
                    b.rotate(dir, colPos, rowPos);
                }
            }
        }
    }

    public boolean atBottom() {
        return (!canMove(0, 1));
    }

    public static boolean canSpawnNewPiece(pieceType type) {
        int middle = (Game.width / 2) - 1;
        if (type == pieceType.BACK_L || type == pieceType.L) return (!(Game.board[2][middle] == 1));
        return (!(Game.board[1][middle] == 1));
    }


    public Piece(pieceType type) {
        blockList = new ArrayList<>();
        localCoords = new ArrayList<>();

        this.type = type;

        rowPos = 0;
        colPos = (Game.width / 2) - 1;

        blockList.add(new Block(0, colPos, 0, 0));
        localCoords.add(new int[]{0,0});
        switch (type) {
            case L -> {
                blockList.add(new Block(0, colPos + 1, 1, 0));
                blockList.add(new Block(1, colPos, 0, 1));
                blockList.add(new Block(2, colPos, 0, 2));
            }
            case BACK_L -> {
                blockList.add(new Block(0, colPos - 1, -1, 0));
                blockList.add(new Block(1, colPos, 0, 1));
                blockList.add(new Block(2, colPos, 0, 2));
            }
            case S -> {
                blockList.add(new Block(0, colPos - 1, -1, 0));
                blockList.add(new Block(1, colPos, 0, 1));
                blockList.add(new Block(1, colPos + 1, 1, 1));
            }
            case BACK_S -> {
                blockList.add(new Block(0, colPos + 1, 1, 0));
                blockList.add(new Block(1, colPos, 0, 1));
                blockList.add(new Block(1, colPos - 1, -1, 1));
            }
            case T -> {
                blockList.add(new Block(0, colPos - 1, -1, 0));
                blockList.add(new Block(0, colPos + 1, 1, 0));
                blockList.add(new Block(1, colPos,0, 1));
            }
            case LINE -> {
                blockList.add(new Block(0, colPos - 1, -1, 0));
                blockList.add(new Block(0, colPos + 1, 1, 0));
                blockList.add(new Block(0, colPos + 2, 2, 0));
            }
            case SQUARE -> {
                blockList.add(new Block(0, colPos + 1, 1, 0));
                blockList.add(new Block(1, colPos, 0, 1));
                blockList.add(new Block(1, colPos + 1, 1, 1));
            }
        }
    }
}
