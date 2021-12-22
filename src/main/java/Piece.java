import java.util.ArrayList;

public class Piece {

    public static ArrayList<Block> blockList;

    public enum pieceType {
        L,
        BACK_L,
        S,
        BACK_S,
        T,
        LINE,
        SQUARE
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
            ArrayList<Block> movedBlocks = new ArrayList<>();
            if (canMove(dx, dy)) {
                for (Block b : blockList) {
                    if (movedBlocks.contains(b)) continue;
                    for (Block b2 : blockList) {
                        if (b.col + dx == b2.col && b.row + dy == b2.row) {
                            b2.move(dx, dy);
                            movedBlocks.add(b2);
                        }
                    }
                    b.move(dx, dy);
                    movedBlocks.add(b);
                }
            }
        }
    }



    public Piece(pieceType type) {
        blockList = new ArrayList<>();
        int middle = (Game.width / 2) - 1;
        blockList.add(new Block(0, middle));
        switch (type) {
            case L -> {
                blockList.add(new Block(0, middle + 1));
                blockList.add(new Block(1, middle));
                blockList.add(new Block(2, middle));
            }
            case BACK_L -> {
                blockList.add(new Block(0, middle + 1));
                blockList.add(new Block(1, middle + 1));
                blockList.add(new Block(2, middle + 1));
            }
            case S -> {
                blockList.add(new Block(0, middle + 1));
                blockList.add(new Block(1, middle + 1));
                blockList.add(new Block(1, middle + 2));
            }
            case BACK_S -> {
                blockList.add(new Block(0, middle - 1));
                blockList.add(new Block(1, middle - 1));
                blockList.add(new Block(1, middle - 2));
            }
            case T -> {
                blockList.add(new Block(0, middle - 1));
                blockList.add(new Block(0, middle + 1));
                blockList.add(new Block(1, middle));
            }
            case LINE -> {
                blockList.add(new Block(0, middle - 1));
                blockList.add(new Block(0, middle + 1));
                blockList.add(new Block(0, middle + 2));
            }
            case SQUARE -> {
                blockList.add(new Block(0, middle + 1));
                blockList.add(new Block(1, middle));
                blockList.add(new Block(1, middle + 1));
            }
        }
    }
}
