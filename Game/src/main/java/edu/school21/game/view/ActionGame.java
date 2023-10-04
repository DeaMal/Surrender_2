package edu.school21.game.view;

import edu.school21.chase.logic.Enemy;
import edu.school21.chase.logic.GameField;
import edu.school21.chase.logic.Player;
import edu.school21.game.logic.Parameters;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ActionGame extends JComponent implements KeyListener, ActionListener {
    private final GameField field;
    private final Parameters parameters;
    private final Player player;
    private final Enemy[] enemies;
    private final int size;
    private final JFrame game;
    private final BufferedImage field2D;
    private final BufferedImage enemy2D;
    private final BufferedImage player2D;
    private final BufferedImage wall2D;
    private final BufferedImage goal2D;
    Timer t = new Timer(5, this);

    public ActionGame(int fieldSize, int wallsCount, int enemyCount, boolean mode, JFrame g) {
        game = g;
        parameters = new Parameters(mode ? "production" : "dev");
        size = fieldSize;
        field = new GameField(size, wallsCount, enemyCount);
        player = new Player(field);
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(field.getEnemiesCoord()[i][0], field.getEnemiesCoord()[i][1], field, player);
        }
        player2D = drawElement(getColor(parameters.getPlayerColor()), parameters.getPlayer(), "/1.png");
        enemy2D = drawElement(getColor(parameters.getEnemyColor()), parameters.getEnemy(), "/2.png");
        wall2D = drawElement(getColor(parameters.getWallColor()), parameters.getWall(), "/3.png");
        goal2D = drawElement(getColor(parameters.getGoalColor()), parameters.getGoal(), "/4.png");
        field2D = drawField();
    }

    public BufferedImage drawField() {
        BufferedImage targetImage = new BufferedImage(size * 20, size * 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2D = targetImage.createGraphics();
        g2D.setColor(getColor(parameters.getEmptyColor()));
        g2D.fillRect(0, 0, size * 20, size * 20);
        for (int i = 1; i < size + 1; i++) {
            for (int j = 1; j < size + 1; j++) {
                char ch = field.getCell(j, i);
                if (ch == '#') {
                    g2D.drawImage(wall2D, null, (j - 1) * 20, (i - 1) * 20);
                } else if (ch == 'O') {
                    g2D.drawImage(goal2D, null, (j - 1) * 20, (i - 1) * 20);
                }
            }
        }
        g2D.dispose();
        return targetImage;
    }

    public BufferedImage drawElement(Color c, char ch, String file) {
        BufferedImage targetImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = targetImage.createGraphics();
        if (parameters.isDebug()) {
            g2D.setColor(c);
            g2D.fillRect(0, 0, 20, 20);
            g2D.setColor(Color.WHITE);
            String fontName = "Microsoft YaHei";
            Font f = new Font(fontName, Font.PLAIN, 20);
            g2D.setFont(f);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            FontMetrics fm = g2D.getFontMetrics(f);
            int textWidth = fm.stringWidth(String.valueOf(ch));
            int widthX = (20 - textWidth) / 2;
            g2D.drawString(String.valueOf(ch), widthX,17);
        } else {
            try {
                g2D.drawImage(ImageIO.read(getClass().getResource(file)), 0, 0, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return targetImage;
    }

    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.drawImage(field2D, null, 0, 0);

        for (Enemy e : enemies) {
            graphics2D.drawImage(enemy2D, null, (e.getMyPointCoordX() - 1) * 20, (e.getMyPointCoordY() - 1) * 20);
        }
        graphics2D.drawImage(player2D, null, (player.getCordX() - 1) * 20, (player.getCordY() - 1) * 20);
        graphics2D.dispose();
        t.start();
    }

    public Color getColor(String col) {
        Color color;
        switch (col.toLowerCase()) {
            case "black":
                color = Color.BLACK;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            case "cyan":
                color = Color.CYAN;
                break;
            case "dark_gray":
                color = Color.DARK_GRAY;
                break;
            case "gray":
                color = Color.GRAY;
                break;
            case "green":
                color = Color.GREEN;
                break;
            case "yellow":
                color = Color.YELLOW;
                break;
            case "light_gray":
                color = Color.LIGHT_GRAY;
                break;
            case "magenta":
                color = Color.MAGENTA;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            case "pink":
                color = Color.PINK;
                break;
            case "red":
                color = Color.RED;
                break;
            default:
                color = Color.WHITE;
        }
        return color;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int movement = -1;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movement = player.movePlayer(3);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movement = player.movePlayer(1);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            movement = player.movePlayer(5);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            movement = player.movePlayer(2);
        }
        if (movement != -1) {
            if (movement == 0) {
                for (Enemy enemy : enemies) {
                    if ((movement = enemy.enemyNextStep()) != 0) {
                        break;
                    }
                }
            }
            if (movement != 0) {
                JOptionPane.showMessageDialog(null,
                        (movement == 1) ? "Surrender, You're Surrounded!" : "You are Winner!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
                game.dispose();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}