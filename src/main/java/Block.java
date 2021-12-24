public class Block {
    public int col, row;
    public int[] localCoords;

    public Block(int inRow, int inCol, int inLocalX, int inLocalY) {
        localCoords = new int[2];
        col = inCol; row = inRow; localCoords[0] = inLocalX; localCoords[1] = inLocalY;
        GamePanel.board[row][col] = 1;
    }

    public boolean canMove(int dx, int dy) {
        if (col + dx >= 0 && col + dx < Game.WIDTH && row + dy >= 0 && row + dy < Game.HEIGHT) {
            if (GamePanel.board[row + dy][col + dx] == 0) {
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

        boolean temp = false;
        for (Block b : Piece.blockList) {
            if (b != this) {
                if (row == b.row && col == b.col) {
                    temp = true;
                }
            }
        }

        if (!temp) GamePanel.board[row][col] = 0;

        col += dx;
        row += dy;


        GamePanel.board[row][col] = 1;

    }

    public boolean canRotate(int dir, int originX, int originY) {
        int newLocalX = localCoords[1] * dir;
        int newLocalY = localCoords[0] * -dir;

        int newCol = originX + newLocalX;
        int newRow = originY + newLocalY;

        if (localCoords[0] == 0 && localCoords[1] == 0) return true;

        if (newCol >= 0 && newCol < Game.WIDTH && newRow >= 0 && newRow < Game.HEIGHT) { //Bounds check
            if (GamePanel.board[newRow][newCol] == 0) {
                return true;
            } else {
                for (Block b : Piece.blockList) { //Check if rotated coordinates match that of another block in the same Piece
                    if (newCol == b.col && newRow == b.row) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void rotate(int dir, int originX, int originY) {
        int newLocalX = localCoords[1] * dir;
        int newLocalY = localCoords[0] * -dir;

        int newCol = originX + newLocalX;
        int newRow = originY + newLocalY;

        boolean temp = false;
        for (Block b : Piece.blockList) {
            if (b != this) {
                if (row == b.row && col == b.col) {
                    temp = true;
                }
            }
        }

        if (!temp) GamePanel.board[row][col] = 0;

        col = newCol;
        row = newRow;
        localCoords[0] = newLocalX;
        localCoords[1] = newLocalY;
        GamePanel.board[row][col] = 1;
    }
}
