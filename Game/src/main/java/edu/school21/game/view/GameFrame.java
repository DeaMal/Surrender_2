package edu.school21.game.view;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(int size, int wallsCount, int enemyCount, boolean mode) {
        super("Game");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(size * 20 + 16, size * 20 + 38);
        setLocation(750, 400);
        setResizable(false);
        ActionGame actionGame = new ActionGame(size, wallsCount, enemyCount, mode, this);
        add(actionGame);
        addKeyListener(actionGame);
        setVisible(true);
    }
}
