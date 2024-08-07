import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {
    @Override
    public void run() {
        JFrame startFrame = new JFrame("2048");
        startFrame.setResizable(false);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startFrame.add(startPanel);
        JButton playGame = new JButton("Play Game");
        playGame.addActionListener(event -> {
            JFrame frame = new JFrame("2048");
            frame.setResizable(false);

            JPanel controls = new JPanel();
            frame.add(controls, BorderLayout.NORTH);

            JLabel score = new JLabel("Score: 0");
            controls.add(score);

            GameManager gm = new GameManager(score);
            frame.add(gm);

            JButton reset = new JButton("Reset");
            reset.addActionListener(e -> {
                gm.reset();
                gm.requestFocusInWindow();
            });
            controls.add(reset);

            JButton undo = new JButton("Undo");
            undo.addActionListener(e -> {
                gm.undo();
                gm.requestFocusInWindow();
            });
            controls.add(undo);

            JButton save = new JButton("Save");
            save.addActionListener(e -> {
                gm.saveProgress();
                gm.requestFocusInWindow();
            });
            controls.add(save);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            startFrame.setVisible(false);

            gm.requestFocusInWindow();
        });
        startPanel.add(playGame);

        JLabel text1 = new JLabel("Use the arrow keys to move the tiles left, right, up, or down.");
        JLabel text2 = new JLabel("Collide two tiles with the same number to combine them. If you get a 2048 " +
                "tile, you win!");
        JLabel text3 = new JLabel("If you cannot make any moves, you lose. Press the reset button to start " +
                "over, the undo button to");
        JLabel text4 = new JLabel("undo your last moves, and press the save button to save your progress and " +
                "resume when you open the game next.");
        startPanel.add(text1);
        startPanel.add(text2);
        startPanel.add(text3);
        startPanel.add(text4);

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}