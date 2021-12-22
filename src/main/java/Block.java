public class Block {
    public int col, row;

    public Block(int inRow, int inCol) {
        col = inCol; row = inRow;
        Game.board[row][col] = 1;
    }

    public boolean canMove(int dx, int dy) {
        if (col + dx >= 0 && col + dx < Game.width && row + dy >= 0 && row + dy < Game.height) {
            if (Game.board[row + dy][col + dx] == 0) {
                return true;
            } else {
                for (Block b : Piece.blockList) {
                    if (col + dx == b.col && row + dy == b.row) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public void move(int dx, int dy) {
        if (canMove(dx, dy)) {
            Game.board[row][col] = 0;
            col += dx;
            row += dy;
            Game.board[row][col] = 1;
        }
    }
}
