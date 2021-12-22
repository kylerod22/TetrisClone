public class Game {

    public static final int width = 10, height = 10;

    public static int[][] board;
    /*Board is a matrix in this format:
    0 1 2 3 4 5 6 7 8 9
    10 11 12 13 14 15 16 17 18 19 ...

    ...
     */

    public Game() {
        board = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
            }
        }

        Piece piece = new Piece(Piece.pieceType.BACK_L);
        piece.move(2, 0);
        print();


    }

    static public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------");
    }

    public static void main(String[] args) {
        new Game();
    }
}
