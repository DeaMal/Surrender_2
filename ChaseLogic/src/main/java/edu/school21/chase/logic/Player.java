package edu.school21.chase.logic;

public class Player {
    private final GameField field;
    private int cordX;
    private int cordY;

    public Player(GameField gameField) {
        field = gameField;
        cordX = field.getPlayerCoord()[0];
        cordY = field.getPlayerCoord()[1];
    }

    public int movePlayer(int direction) {
        int result = -1, nextX = 0, nextY = 0;
        if (direction == 5) {
            nextY = -1;
        } else if (direction == 3) {
            nextX = 1;
        } else if (direction == 2) {
            nextY = 1;
        } else if (direction == 1) {
            nextX = -1;
        } else if (direction == 9 || direction == 8) {
            return direction;
        } else {
            return result;
        }
        if (field.canIStepOnIt(cordX + nextX, cordY + nextY)) {
            if (field.getCell(cordX + nextX, cordY + nextY) == 'O') {
                field.setCell(cordX, cordY, '.');
                result = 2;
            } else if (field.getCell(cordX + nextX, cordY + nextY) == 'X') {
                field.setCell(cordX, cordY, '.');
                result = 1;
            } else {
                field.setCell(cordX, cordY, '.');
                cordX += nextX;
                cordY += nextY;
                field.setCell(cordX, cordY, 'o');
                result = 0;
            }
        }
        return result;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }
}
