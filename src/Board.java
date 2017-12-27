public class Board {

    private int width;
    private int height;
    private Cell[][] board;

    // current height in each column (height of highest disk)
    private int[] heightInCols;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Cell[height][width];

        for (int rows = 0; rows < height; rows++) {
            for (int cols = 0; cols < width; cols++) {
                this.board[rows][cols] = Cell.EMPTY;
            }
        }

        // auto-initialized with 0s
        this.heightInCols = new int[width];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Cell getCellValue(int row, int column) {
        return this.board[row][column];
    }

    public int getHeightinCol(int column) {
        return this.heightInCols[column];
    }

    public void incrementHeightInCol(int column) {
        this.heightInCols[column]++;
    }

    public void setCell(Cell cell, int row, int column) {
        this.board[row][column] = cell;
    }
}
