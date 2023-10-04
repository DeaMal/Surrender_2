package edu.school21.chase.logic;

public class Enemy {
    private final GameField field;
    private final Player player;
    private int myPointCoordX;
    private int myPointCoordY;
    public Enemy(int MyPointCoordX, int MyPointCoordY, GameField Field, Player user) {
        myPointCoordX = MyPointCoordX;
        myPointCoordY = MyPointCoordY;
        field = Field;
        player = user;
    }

    public int enemyNextStep() {
        int plCoordX = player.getCordX();
        int plCoordY = player.getCordY();
        int stepX = 0;
        int stepY = 0;
        if (plCoordX > myPointCoordX) {
            stepX++;
        } else if (plCoordX < myPointCoordX) {
            stepX--;
        }
        int differenceX = Math.abs(plCoordX - myPointCoordX);
        int differenceY = Math.abs(plCoordY - myPointCoordY);
        if (plCoordY > myPointCoordY) {
            stepY++;
        } else {
            stepY--;
        }
        boolean step_result;
        if (differenceX >= differenceY) {
            step_result = field.canEnemyStepOnIt(myPointCoordX + stepX, myPointCoordY);
            if (step_result) {
                field.setCell(myPointCoordX + stepX, myPointCoordY, 'X');
                field.setCell(myPointCoordX, myPointCoordY, '.');
                myPointCoordX += stepX;
            }
        } else {
            step_result = field.canEnemyStepOnIt(myPointCoordX, myPointCoordY + stepY);
            if (step_result) {
                field.setCell(myPointCoordX, myPointCoordY + stepY, 'X');
                field.setCell(myPointCoordX, myPointCoordY, '.');
                myPointCoordY += stepY;
            }
        }
        return (plCoordX==myPointCoordX&&plCoordY==myPointCoordY)? 1: 0;
    }

    public int getMyPointCoordX() {
        return myPointCoordX;
    }

    public int getMyPointCoordY() {
        return myPointCoordY;
    }
}
