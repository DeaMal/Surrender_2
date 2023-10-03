package edu.school21.chase.logic;

import java.util.*;

public class GameField {

    private char[][] field;
    private char[][] fieldBackup;
    private final int fieldSize;
    private final int obstacles;
    private final int enemies;
    private int[] playerCoord = new int[2];
    private int[] playerCoordBackup = new int[2];
    private int[] playerCoordForCheck = new int[2];

    private int[] targetCoord = new int[2];
    private Integer[][] enemiesCoord;
    private Map<Integer, ArrayList<Integer>> obstaclesCoord = new HashMap<>();

    public GameField(int fieldSizeIn, int obstaclesIn, int enemiesIn) {
        fieldSize = fieldSizeIn + 2;
        obstacles = obstaclesIn;
        enemies = enemiesIn;
        enemiesCoord = new Integer[enemiesIn][2];
        fieldGenerator();
    }
    private void fieldGenerator() {
        if((obstacles + enemies + 2) > fieldSize * fieldSize) {
            throw new IllegalParametersException("Transaction not found");
        }
        int genStopLoss = fieldSize * 10;
        do{
            fieldInitialInit();
            randomizeEssences('#', obstacles);
            randomizeEssences('O', 1);
            randomizeEssences('X', enemies);
            randomizeEssences('o', 1);
            --genStopLoss;
        } while (!wayCheck() && (genStopLoss != 0));
        if(genStopLoss != 0) {
            for(int i = 0; i < fieldSize; ++i) {
                for(int j = 0; j < fieldSize; ++j) {
                    field[i][j] = fieldBackup[i][j];
                }
            }
            playerCoord[0] = playerCoordBackup[0];
            playerCoord[1] = playerCoordBackup[1];
        } else {
            throw new IllegalParametersException("Transaction not found");
        }
    }

    private boolean wayCheck() {
        Random rd = new Random();
        int searchStopLoss = 0;
        boolean gotIt = false;
        boolean manInTheBox = false;
        for(int i = 0; i < fieldSize; ++i) {
            for(int j = 0; j < fieldSize; ++j) {
                fieldBackup[i][j] = field[i][j];
            }
        }
        for(int ways = 0; ((ways < fieldSize) && (!manInTheBox) && (!gotIt)); ++ways) {
            int[][] nextCoords = new int[4][2];
            while ((!gotIt) && (searchStopLoss < fieldSize * fieldSize)) {
                playerCoordForCheck[0] = playerCoord[0];
                playerCoordForCheck[1] = playerCoord[1];
                nextCoords[0][0] = playerCoordForCheck[0] - 1;
                nextCoords[0][1] = playerCoordForCheck[1];
                nextCoords[1][0] = playerCoordForCheck[0];
                nextCoords[1][1] = playerCoordForCheck[1] - 1;
                nextCoords[2][0] = playerCoordForCheck[0] + 1;
                nextCoords[2][1] = playerCoordForCheck[1];
                nextCoords[3][0] = playerCoordForCheck[0];
                nextCoords[3][1] = playerCoordForCheck[1] + 1;
                double[] geometrRange = new double[4];
                double[] geometrRangeInObs = new double[4];
                for (int i = 0; i < 4; ++i) {
                    geometrRangeInObs[i] = 0;
                }
                for (int i = 0; i < 4; ++i) {
                    geometrRange[i] = Math.sqrt(Math.pow(nextCoords[i][0] - targetCoord[0], 2) +
                            Math.pow(nextCoords[i][1] - targetCoord[1], 2));
                    if (!canIStepOnIt(nextCoords[i][0], nextCoords[i][1])) {
                        geometrRangeInObs[i] = 1;
                    }
                }
                double currMinGeomRange = geometrRange[0];
                int currWay = 0;
                for (int i = 1; i < 4; ++i) {
                    if ((currMinGeomRange > geometrRange[i]) && (geometrRange[i] >= 0)) {
                        currMinGeomRange = geometrRange[i];
                        currWay = i;
                    }
                }
                if((geometrRangeInObs[0] == 1) && (geometrRangeInObs[1] == 1)  && (geometrRangeInObs[2] == 1)
                        && (geometrRangeInObs[3] == 1)) {
                    manInTheBox = true;
                    break;
                }
                do {
                    if (geometrRangeInObs[currWay] == 1) {
                        if (rd.nextBoolean()) {
                            currWay = currWayInvrement(currWay);
                        } else {
                            currWay = currWayDecrement(currWay);
                        }
                    }
                } while (geometrRangeInObs[currWay] == 1);
                switch (currWay) {
                    case (0):
                        --playerCoordForCheck[0];
                        break;
                    case (1):
                        --playerCoordForCheck[1];
                        break;
                    case (2):
                        ++playerCoordForCheck[0];
                        break;
                    case (3):
                        ++playerCoordForCheck[1];
                        break;
                }
                field[playerCoord[1]][playerCoord[0]] = '.';
                playerCoord[0] = playerCoordForCheck[0];
                playerCoord[1] = playerCoordForCheck[1];
                field[playerCoordForCheck[1]][playerCoordForCheck[0]] = 'o';
                if ((playerCoordForCheck[0] == targetCoord[0]) && (playerCoordForCheck[1] == targetCoord[1])) {
                    gotIt = true;
                } else {
                    ++searchStopLoss;
                }
            }
        }
        return gotIt;
    }

    private int currWayInvrement(int currWayIn) {
        if(currWayIn == 3) {
            return 0;
        } else {
            return currWayIn + 1;
        }
    }

    private int currWayDecrement(int currWayIn) {
        if(currWayIn == 0) {
            return 3;
        } else {
            return currWayIn - 1;
        }
    }

    public boolean canIStepOnIt(int xcoord, int ycoord) {
        if((field[ycoord][xcoord] == '.') || (field[ycoord][xcoord] == 'O') || (field[ycoord][xcoord] == 'X')
                || (field[ycoord][xcoord] == 'o')) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canEnemyStepOnIt(int xcoord, int ycoord) {
        if((field[ycoord][xcoord] == '.') || (field[ycoord][xcoord] == 'o')) {
            return true;
        } else {
            return false;
        }
    }

    private void randomizeEssences(char essence, int num) {
        ArrayList<Integer> dummy;
        Random rand = new Random();
        Integer currRandXCoord;
        Integer currRandYCoord;
        int enemiesIterator = 0;
        if(essence == '#') {
            obstaclesCoord.clear();
            for(int i = 0; i < fieldSize; ++i) {
                field[i][0] = '#';
                field[i][fieldSize - 1] = '#';
                field[0][i] = '#';
                field[fieldSize - 1][i] = '#';
            }
        }
        for (int i = 0; i < num; ++i) {
            do {
                currRandXCoord = Math.abs(rand.nextInt() % (fieldSize - 2)) + 1;
                currRandYCoord = Math.abs(rand.nextInt() % (fieldSize - 2)) + 1;
            } while ((obstaclesCoord.containsKey(currRandYCoord)) &&
                    (obstaclesCoord.get(currRandYCoord).contains(currRandXCoord)));
            if(essence == 'O') {
                targetCoord[0] = currRandXCoord;
                targetCoord[1] = currRandYCoord;
            } else if(essence == 'o') {
                playerCoord[0] = currRandXCoord;
                playerCoord[1] = currRandYCoord;
                playerCoordBackup[0] = currRandXCoord;
                playerCoordBackup[1] = currRandYCoord;
            } else if (essence == 'X') {
                enemiesCoord[enemiesIterator][0] = currRandXCoord;
                enemiesCoord[enemiesIterator][1] = currRandYCoord;
                ++enemiesIterator;
            }
            if (!obstaclesCoord.containsKey(currRandYCoord)) {
                dummy = new ArrayList<>();
                dummy.add(currRandXCoord);
                obstaclesCoord.put(currRandYCoord, dummy);
            } else {
                obstaclesCoord.get(currRandYCoord).add(currRandXCoord);
            }
        }
        for(int i = 1; i < fieldSize - 1; ++i) {
            for(int j = 1; j < fieldSize - 1; ++j) {
                if((obstaclesCoord.containsKey(i)) &&
                        (obstaclesCoord.get(i).contains(j)) && (field[i][j] != '#') && (field[i][j] != 'O')
                        && (field[i][j] != 'X')) {
                    field[i][j] = essence;
                }
            }
        }
    }

    private void fieldInitialInit() {
        fieldBackup = new char[fieldSize][fieldSize];
        field = new char[fieldSize][fieldSize];
        for (int i = 1; i < fieldSize - 1; ++i) {
            for (int j = 1; j < fieldSize - 1; ++j) {
                field[i][j] = '.';
            }
        }
    }

    public void gameFieldPrintBW() {
        for(int i = 0; i < fieldSize; ++i) {
            for(int j = 0; j < fieldSize; ++j) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public char getCell(int x, int y) {
        return field[y][x];
    }

    public void setCell(int x, int y, char ch) {
        field[y][x] = ch;
    }

    public int[] getPlayerCoord(){
        return playerCoord;
    }

    public Integer[][] getEnemiesCoord(){
        return enemiesCoord;
    }

    public void setEnemiesCoord(Integer[][] newEnemiesCoord){
        for(int i = 0; i < newEnemiesCoord.length; ++i) {
            enemiesCoord[i][0] = newEnemiesCoord[i][0];
            enemiesCoord[i][1] = newEnemiesCoord[i][1];
        }
    }
}
