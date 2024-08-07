import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

public class GameManager extends JPanel {
    private GameBoard gb;
    private LinkedList<GameBoard> moveList;
    private JLabel score;

    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 500;
    public static final int TILE_SIZE = BOARD_WIDTH / 4;

    public GameManager(JLabel score) {
        this.score = score;
        moveList = new LinkedList<>();

        File f = new File("savedProgress.txt");
        if (f.exists()) {
            loadProgress();
            if (!moveList.isEmpty()) {
                gb = new GameBoard(moveList.peekLast());
            } else {
                gb = new GameBoard();
                moveList.add(new GameBoard(gb));
            }
        } else {
            gb = new GameBoard();
            moveList.add(new GameBoard(gb));
        }

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    makeMove("left");
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    makeMove("right");
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    makeMove("down");
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    makeMove("up");
                }
            }
        });
    }

    public void makeMove(String move) {
        if (gb.gameWon() || gb.gameLost()) {
            return;
        }

        switch (move) {
            case "left" -> gb.leftMove();
            case "right" -> gb.rightMove();
            case "up" -> gb.upMove();
            case "down" -> gb.downMove();
        }

        if (!gb.equals(moveList.peekLast())) {
            gb.addNewTile();
            moveList.add(new GameBoard(gb));
            if (gb.gameWon()) {
                score.setText("You win!");
            } else if (gb.gameLost()) {
                score.setText("You lose!");
            } else {
                score.setText("Score: " + gb.getScore());
            }
            repaint();
        }
    }

    public void undo() {
        if (moveList.size() > 1) {
            moveList.removeLast();
            gb = new GameBoard(moveList.peekLast());
            score.setText("Score: " + gb.getScore());
            repaint();
        }
    }

    public void saveProgress() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("savedProgress.txt"));
            for (GameBoard gb : moveList) {
                bw.write(Integer.toString(gb.getScore()));
                bw.newLine();
                for (int[] row : gb.getBoard()) {
                    for (int num : row) {
                        bw.write(num + " ");
                    }
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProgress() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("savedProgress.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                int score = Integer.parseInt(line);
                int[][] board = new int[4][4];
                for (int i = 0; i < 4; i++) {
                    line = br.readLine();
                    String[] nums = line.split(" ");
                    for (int j = 0; j < 4; j++) {
                        board[i][j] = Integer.parseInt(nums[j]);
                    }
                }
                GameBoard gb = new GameBoard(board, score);
                moveList.add(gb);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        gb = new GameBoard();
        moveList = new LinkedList<>();
        moveList.add(new GameBoard(gb));
        score.setText("Score: 0");
        repaint();
    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);

        gc.setColor(new Color(0xbbac9f));
        gc.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        for (int i = 0; i < gb.getBoard().length; i++) {
            for (int j = 0; j < gb.getBoard()[i].length; j++) {
                int number = gb.getBoard()[i][j];
                drawTile(gc, number, i, j);
            }
        }
    }

    private void drawTile(Graphics gc, int number, int row, int col) {
        int x = col * TILE_SIZE;
        int y = row * TILE_SIZE;

        gc.setColor(getTileColor(number));
        gc.fillRect(x + (int) (TILE_SIZE * 0.05), y + (int) (TILE_SIZE * 0.05),
                (int) (TILE_SIZE * 0.9), (int) (TILE_SIZE * 0.9));

        if (number == 2 || number == 4) {
            gc.setColor(new Color(0x766d65));
        } else {
            gc.setColor(Color.WHITE);
        }
        gc.setFont(new Font("Arial", Font.BOLD, 50));
        String text = number > 0 ? Integer.toString(number) : "";
        int textX = x + TILE_SIZE / 2 - gc.getFontMetrics().stringWidth(text) / 2;
        int textY = y + TILE_SIZE / 2 + gc.getFontMetrics().getAscent() / 2;
        gc.drawString(text, textX, textY);
    }

    private Color getTileColor(int number) {
        if (number >= 128) {
            return new Color(0xEDCF72);
        }
        return switch (number) {
            case 2 -> new Color(0xEEE4DA);
            case 4 -> new Color(0xEDE0C8);
            case 8 -> new Color(0xF2B179);
            case 16 -> new Color(0xF59563);
            case 32 -> new Color(0xF67C5F);
            case 64 -> new Color(0xF65E3B);
            default -> new Color(0xCCC0B3);
        };
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}

