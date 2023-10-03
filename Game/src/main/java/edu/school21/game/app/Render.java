package edu.school21.game.app;

import edu.school21.game.logic.Parameters;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.chase.logic.GameField;

public class Render {
    private final GameField field;
    private final int size;
    char enemy;
    char player;
    char wall;
    char goal;
    char empty;
    Boolean isDebug;
    String enemyColor;
    String playerColor;
    String wallColor;
    String goalColor;
    String emptyColor;
    String currentColor;
    ColoredPrinter cp;
    Parameters parameters;

    public Render(GameField Field, int Size, Parameters parameters1 ) {
        parameters = parameters1;
        initParameters();
        field = Field;
        size = Size + 1;
        cp = new ColoredPrinter();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public  void Rendering() {
        if (!isDebug) {
            clearScreen();
        }
        for(int i = 1; i < size; i++ ) {
            for (int j=1;j< size;j++) {
                char ch = field.getCell(j,i);
                cp.print(
                            transcriptChar(
                            ch),
                            Ansi.Attribute.NONE,
                            Ansi.FColor.NONE,
                            Ansi.BColor.valueOf(currentColor));
            }
            System.out.println();
        }
    }
    public char transcriptChar (char Ch) {
        char res = '?';
        if ( Ch == 'X') {
            res = enemy;
            currentColor = enemyColor;
        } else if (Ch == 'o') {
            res = player;
            currentColor = playerColor;
        } else if (Ch == '#') {
            res = wall;
            currentColor = wallColor;
        } else if (Ch == 'O') {
            res = goal;
            currentColor = goalColor;
        } else if (Ch == '.') {
            res = empty;
            currentColor = emptyColor;
        }
        return res;
    }

    public void initParameters () {
        enemy = parameters.getEnemy();
        player = parameters.getPlayer();
        wall = parameters.getWall();
        goal = parameters.getGoal();
        empty  = parameters.getEmpty();
        enemyColor =  parameters.getEnemyColor();
        playerColor = parameters.getPlayerColor();
        wallColor = parameters. getWallColor();
        goalColor =  parameters.getGoalColor();
        emptyColor = parameters.getEmptyColor();
        isDebug = parameters.isDebug();
    }
}
