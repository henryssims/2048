public class GameBoard {
    private int score;
    private int[][] board;

    public GameBoard() {
        this.score = 0;
        this.board = new int[4][4];
        initBoard();
    }

    public GameBoard(int[][] board, int score) {
        this.score = score;
        this.board = board;
    }

    public GameBoard(GameBoard that) {
        int[][] newBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newBoard[i][j] = that.getBoard()[i][j];
            }
        }
        this.board = newBoard;
        this.score = that.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GameBoard gb)) {
            return false;
        }
        if (this.score != gb.getScore()) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.board[i][j] != gb.getBoard()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getScore() {
        return this.score;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void initBoard() {
        int x1 = (int)  (Math.random() * 4);
        int y1 = (int)  (Math.random() * 4);
        int val1 = Math.random() < 0.9 ? 2 : 4;

        int x2 = (int)  (Math.random() * 4);
        int y2 = (int)  (Math.random() * 4);
        while (x1 == x2 && y1 == y2) {
            x2 = (int)  (Math.random() * 4);
            y2 = (int)  (Math.random() * 4);
        }
        int val2 = Math.random() < 0.9 ? 2 : 4;

        this.board[y1][x1] = val1;
        this.board[y2][x2] = val2;
    }

    public void addNewTile() {
        int x = (int)  (Math.random() * 4);
        int y = (int)  (Math.random() * 4);
        while (this.board[y][x] != 0) {
            x = (int)  (Math.random() * 4);
            y = (int)  (Math.random() * 4);
        }
        int val = Math.random() < 0.9 ? 2 : 4;
        this.board[y][x] = val;
    }

    private void slideLeft(int[] row, int i) {
        while (row[i] == 0) {
            boolean allZeros = true;
            for (int j = i; j < row.length - 1; j++) {
                if (row[j + 1] != 0) {
                    allZeros = false;
                }
                row[j] = row[j + 1];
                row[j + 1] = 0;
            }
            if (allZeros) {
                break;
            }
        }
    }

    public void leftMove() {
        for (int[] row : board) {
            for (int i = 0; i < row.length; i++) {
                slideLeft(row, i);
                if (i == 0) {
                    continue;
                }
                if (row[i] == row[i - 1]) {
                    row[i - 1] *= 2;
                    row[i] = 0;
                    this.score += row[i - 1];
                }
                slideLeft(row, i);
            }
        }
    }

    private void slideRight(int[] row, int i) {
        while (row[i] == 0) {
            boolean allZeros = true;
            for (int j = i; j > 0; j--) {
                if (row[j - 1] != 0) {
                    allZeros = false;
                }
                row[j] = row[j - 1];
                row[j - 1] = 0;
            }
            if (allZeros) {
                break;
            }
        }
    }

    public void rightMove() {
        for (int[] row : board) {
            for (int i = row.length - 1; i >= 0; i--) {
                slideRight(row, i);
                if (i == row.length - 1) {
                    continue;
                }
                if (row[i] == row[i + 1]) {
                    row[i + 1] *= 2;
                    row[i] = 0;
                    this.score += row[i + 1];
                }
                slideRight(row, i);
            }
        }
    }

    private void slideUp(int x, int y) {
        while (board[y][x] == 0) {
            boolean allZeros = true;
            for (int j = y; j < board.length - 1; j++) {
                if (board[j + 1][x] != 0) {
                    allZeros = false;
                }
                board[j][x] = board[j + 1][x];
                board[j + 1][x] = 0;
            }
            if (allZeros) {
                break;
            }
        }
    }

    public void upMove() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                slideUp(x, y);
                if (y == 0) {
                    continue;
                }
                if (board[y][x] == board[y - 1][x]) {
                    board[y - 1][x] *= 2;
                    board[y][x] = 0;
                    this.score += board[y - 1][x];
                }
                slideUp(x, y);
            }
        }
    }

    private void slideDown(int x, int y) {
        while (board[y][x] == 0) {
            boolean allZeros = true;
            for (int j = y; j > 0; j--) {
                if (board[j - 1][x] != 0) {
                    allZeros = false;
                }
                board[j][x] = board[j - 1][x];
                board[j - 1][x] = 0;
            }
            if (allZeros) {
                break;
            }
        }
    }

    public void downMove() {
        for (int y = board.length - 1; y > 0; y--) {
            for (int x = 0; x < board[y].length; x++) {
                slideDown(x, y);
                if (y == board.length - 1) {
                    continue;
                }
                if (board[y][x] == board[y + 1][x]) {
                    board[y + 1][x] *= 2;
                    board[y][x] = 0;
                    this.score += board[y + 1][x];
                }
                slideDown(x, y);
            }
        }
    }

    public boolean gameWon() {
        for (int[] row : board) {
            for (int i : row) {
                if (i == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameLost() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == 0) {
                    return false;
                }
                if (x < board[y].length - 1 && board[y][x] == board[y][x + 1]) {
                    return false;
                }
                if (y < board.length - 1 && board[y][x] == board[y + 1][x]) {
                    return false;
                }
            }
        }
        return true;
    }
}
