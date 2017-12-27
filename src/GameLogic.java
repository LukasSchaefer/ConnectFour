import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private int boardWidth;
    private int boardHeight;

    private GameStatus gameStatus;

    private Board board;

    // chain of winning cells (Empty if gameStatus is not yet a winning state)
    private List<Pair<Integer>> winnerChain;

    // position of current pointer (column)
    private int pointerPosition;

    public GameLogic(int width, int height) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.board = new Board(width, height);
        this.pointerPosition = 0;
        this.gameStatus = GameStatus.RUNNING;

        this.winnerChain = new ArrayList<>();
    }


    // returns if the move was successful (only false if the column is already full)
    public boolean dropDisk(Player player, int column) {
        // column is already full
        int heightInColumn = board.getHeightinCol(column);
        if (heightInColumn == boardHeight) {
            return false;
        }

        Cell newCellValue;
        if (player == Player.RED) {
            newCellValue = Cell.REDDISK;
        } else {
            newCellValue = Cell.YELLOWDISK;
        }

        board.setCell(newCellValue, boardHeight - 1 - heightInColumn, column);
        board.incrementHeightInCol(column);

        updateGameStatus(newCellValue, boardHeight - 1 - heightInColumn, column);
        return true;
    }

    public Cell getCellValue(int row, int column) {
        return board.getCellValue(row, column);
    }

    public void movePointerLeft() {
        if (pointerPosition > 0) {
            pointerPosition--;
        }
    }

    public void movePointerRight() {
        if (pointerPosition < boardHeight) {
            pointerPosition++;
        }
    }

    public int getPointerPosition() {
        return pointerPosition;
    }

    // parameters are new value and position in board of last change -> more efficient to compute if corresponding player won
    private void updateGameStatus(Cell newCellValue, int row, int column) {
        // row-check

        // chain of same color-cells in a row to the right
        int rightChain = 0;
        for (int c = column + 1; c < boardWidth; c++) {
            if (board.getCellValue(row, c) == newCellValue) {
                // found another corresponding cell to the right
                rightChain++;
            } else {
                // chain of same color-cells is interrupted
                break;
            }
        }

        // chain of same color-cells in a row to the left
        int leftChain = 0;
        for (int c = column - 1; c > 0; c--) {
            if (board.getCellValue(row, c) == newCellValue) {
                // found another corresponding cell to the right
                leftChain++;
            } else {
                // chain of same color-cells is interrupted
                break;
            }
        }

        // check if player corresponding to newCellValue won due to 4 in the row
        if (rightChain + leftChain + 1 >= 4) {
            // check which player has won
            if (newCellValue == Cell.REDDISK) {
                gameStatus = GameStatus.REDWIN;
            } else {
                gameStatus = GameStatus.YELLOWWIN;
            }

            // set winnerChain
            for (int i = 1; i <= leftChain; i++) {
                winnerChain.add(new Pair<>(row, column - i));
            }

            winnerChain.add(new Pair<>(row, column));

            for (int i = 1; i <= rightChain; i++) {
                winnerChain.add(new Pair<>(row, column + i));
            }

            return;
        }

        // column-check

        // chain of same color-cells in the column downwards
        int downChain = 0;
        for (int r = row + 1; r < boardHeight; r++) {
            if (board.getCellValue(r, column) == newCellValue) {
                // found another corresponding cell below
                downChain++;
            } else {
                // chain of same color-cells is interrupted
                break;
            }
        }

        // chain of same color-cells in the column upwards
        int upChain = 0;
        for (int r = row - 1; r > 0; r--) {
            if (board.getCellValue(r, column) == newCellValue) {
                // found another corresponding cell above
                upChain++;
            } else {
                // chain of same color-cells is interrupted
                break;
            }
        }

        // check if player corresponding to newCellValue won due to 4 in the column
        if (upChain + downChain + 1 >= 4) {
            // check which player has won
            if (newCellValue == Cell.REDDISK) {
                gameStatus = GameStatus.REDWIN;
            } else {
                gameStatus = GameStatus.YELLOWWIN;
            }

            // set winnerChain
            for (int i = 1; i <= downChain; i++) {
                winnerChain.add(new Pair<>(row - i, column));
            }

            winnerChain.add(new Pair<>(row, column));

            for (int i = 1; i <= upChain; i++) {
                winnerChain.add(new Pair<>(row + i, column));
            }

            return;
        }

        // diagonal-check
        // upper-left to lower-right
        int upLeftChain = 0;
        int i = 1;
        while (row - i > 0 && column - i > 0) {
            if (board.getCellValue(row - i, column - i) == newCellValue) {
                upLeftChain++;
            }
            i++;
        }

        int downRightChain = 0;
        i = 1;
        while (row + i < boardHeight && column + i < boardWidth) {
            if (board.getCellValue(row + i, column + i) == newCellValue) {
                downRightChain++;
            }
            i++;
        }

        // check if player corresponding to newCellValue won due to 4 in the diagonal
        if (upLeftChain + downRightChain + 1 >= 4) {
            // check which player has won
            if (newCellValue == Cell.REDDISK) {
                gameStatus = GameStatus.REDWIN;
            } else {
                gameStatus = GameStatus.YELLOWWIN;
            }

            // set winnerChain
            for (int j = 1; j <= upLeftChain; j++) {
                winnerChain.add(new Pair<>(row - j, column - j));
            }

            winnerChain.add(new Pair<>(row, column));

            for (int j = 1; j <= downRightChain; j++) {
                winnerChain.add(new Pair<>(row + j, column + j));
            }

            return;
        }

        // lower-left to upper-right
        int downLeftChain = 0;
        i = 1;
        while (row + i < boardHeight && column - i > 0) {
            if (board.getCellValue(row + i, column - i) == newCellValue) {
               downLeftChain++;
            }
            i++;
        }

        int upRightChain = 0;
        i = 1;
        while (row - i > 0 && column + i < boardWidth) {
            if (board.getCellValue(row - i, column + i) == newCellValue) {
                upRightChain++;
            }
            i++;
        }

        // check if player corresponding to newCellValue won due to 4 in the diagonal
        if (downLeftChain + upRightChain + 1 >= 4) {
            // check which player has won
            if (newCellValue == Cell.REDDISK) {
                gameStatus = GameStatus.REDWIN;
            } else {
                gameStatus = GameStatus.YELLOWWIN;
            }

            // set winnerChain
            for (int j = 1; j <= downLeftChain; j++) {
                winnerChain.add(new Pair<>(row + j, column - j));
            }

            winnerChain.add(new Pair<>(row, column));

            for (int j = 1; j <= upRightChain; j++) {
                winnerChain.add(new Pair<>(row - j, column + j));
            }
        }
    }

    public GameStatus getCurrentGameStatus() {
        return gameStatus;
    }

    public List<Pair<Integer>> getWinnerChain() {
        return winnerChain;
    }
}
