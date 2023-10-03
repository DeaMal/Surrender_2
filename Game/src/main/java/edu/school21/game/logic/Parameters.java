package edu.school21.game.logic;

import edu.school21.game.app.Render;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Parameters {
    private boolean debug = true;
    private final char enemy;
    private final char player;
    private final char wall;
    private final char goal;
    private final char empty;
    private final String enemyColor;
    private final String playerColor;
    private final String wallColor;
    private final String goalColor;
    private final String emptyColor;

    public Parameters(String profile) {
        if (profile.equals("production")) {
            debug = false;
        } else if (!profile.equals("dev")) {
            System.err.printf("Profile not found: %S\n", profile);
            System.exit(-1);
        }
        Properties prop = new Properties();
        try {
            prop.load(Objects.requireNonNull(Render.class.getResourceAsStream("/application-" + profile + ".properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enemy = prop.getProperty("enemy.char").charAt(0);
        player = prop.getProperty("player.char").charAt(0);
        wall = prop.getProperty("wall.char").charAt(0);
        goal = prop.getProperty("goal.char").charAt(0);
        if (!prop.getProperty("empty.char").isEmpty()) {
            empty = prop.getProperty("empty.char").charAt(0);
        } else {
            empty = ' ';
        }
        enemyColor =  prop.getProperty("enemy.color");
        playerColor = prop.getProperty("player.color");
        wallColor = prop.getProperty("wall.color");
        goalColor =  prop.getProperty("goal.color");
        emptyColor = prop.getProperty("empty.color");
    }

    public boolean isDebug() {
        return debug;
    }

    public char getEnemy() {
        return enemy;
    }

    public char getPlayer() {
        return player;
    }

    public char getWall() {
        return wall;
    }

    public char getGoal() {
        return goal;
    }

    public char getEmpty() {
        return empty;
    }

    public String getEnemyColor() {
        return enemyColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getWallColor() {
        return wallColor;
    }

    public String getGoalColor() {
        return goalColor;
    }

    public String getEmptyColor() {
        return emptyColor;
    }
}
