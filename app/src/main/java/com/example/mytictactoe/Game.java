package com.example.mytictactoe;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private Tile[][] board;

    // extra stats
    private static int totMoves = 0;
    private static int totGames = 0;
    private static int totDraws = 0;
    private static int playerOneWins = 0;
    private static int playerTwoWins = 0;
    private static int aiWins = 0;
    private static int aiDefeated = 0;

    private Boolean playerOneTurn; // true if it is player 1's turn false if 2's turn
    public boolean aiEnabled = false;
    private int movesPlayed = 0;
    private Boolean gameOver;
    private ArrayList<String> freeSpots;

    public Game() {
        totGames++;
        freeSpots = initFreeSpots();
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i=0; i<BOARD_SIZE; i++) {
            for (int j=0; j<BOARD_SIZE; j++) {
                board[i][j] = Tile.BLANK;
            }
        }

        playerOneTurn = true;
        gameOver = false;
    }

    public void switchAI() {
        if (aiEnabled) {
            aiEnabled = false;
        } else {
            aiEnabled = true;
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    public final int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public boolean onesTurn() {
        return playerOneTurn;
    }

    private static ArrayList<String> initFreeSpots() {
        ArrayList<String> holder = new ArrayList<String>();
        holder.add("button0");
        holder.add("button1");
        holder.add("button2");
        holder.add("button3");
        holder.add("button4");
        holder.add("button5");
        holder.add("button6");
        holder.add("button7");
        holder.add("button8");
        return holder;
    }

    public static HashMap<String,Integer> retrieveStats() {
        HashMap<String,Integer> returnSender = new HashMap<String,Integer>();
        returnSender.put("total moves",totMoves);
        returnSender.put("total games",totGames);
        returnSender.put("total draws",totDraws);
        returnSender.put("player one wins",playerOneWins);
        returnSender.put("player two wins",playerTwoWins);
        returnSender.put("ai wins",aiWins);
        returnSender.put("ai defeated",aiDefeated);
        return returnSender;
    }

    public String aiMove() {
        Random AI = new Random();
        int randNum = AI.nextInt(freeSpots.size());
        String button = freeSpots.get(randNum);
        freeSpots.remove(button);
        return button;
    }

    public Tile choose(int row, int column) {
        Tile state = board[row][column];

        if(state == Tile.BLANK) {
            movesPlayed++;
            totMoves++;
            if (playerOneTurn) {
                board[row][column] = Tile.CROSS;
                playerOneTurn = false;
                return Tile.CROSS;
            } else {
                board[row][column] = Tile.CIRCLE;
                playerOneTurn = true;
                return Tile.CIRCLE;
            }
        }
        return Tile.INVALID;
    }

    public GameState won() {
        GameState xyCheck, diaCheck, drawDraw;

        xyCheck= xyWin();
        diaCheck = diaWin();

        if (xyCheck == GameState.PLAYER_ONE || xyCheck == GameState.PLAYER_TWO ) {
            gameOver = true;
            return xyCheck;
        } else if (diaCheck == GameState.PLAYER_ONE || diaCheck == GameState.PLAYER_TWO ) {
            gameOver = true;
            return diaCheck;
        } else {
            drawDraw = drawCheck();

            if (drawDraw == GameState.DRAW) {
                gameOver = true;
                return drawDraw;
            } else {
                return GameState.IN_PROGRESS;
            }
        }
    }

    public boolean stillInProgress() {
        return !gameOver;
    }
    public boolean aiTurn() {
        return !playerOneTurn;
    }

    private GameState xyWin() {
        int play1c, play1r, play2c, play2r;

        for (int i = 0; i < BOARD_SIZE; i++) {
            play1c = play1r = play2c = play2r =0;

            for (int j = 0; j < BOARD_SIZE; j++) {
                //check if there is a row win
                if (board[i][j] == Tile.CROSS) {
                    play1r++;
                } else if (board[i][j] == Tile.CIRCLE) {
                    play2r++;
                }

                //check if there is a column win
                if (board[j][i] == Tile.CROSS) {
                    play1c++;
                } else if (board[j][i] == Tile.CIRCLE) {
                    play2c++;
                }
            }

            if (play1c == 3 || play1r == 3) {
                 if (aiEnabled) {aiDefeated++;} else {playerOneWins++;}
                return GameState.PLAYER_ONE;
            } else if (play2c == 3 || play2r == 3) {
                if (aiEnabled) {aiWins++;} else {playerOneWins++;}
                return GameState.PLAYER_TWO;
            }
        }
        return GameState.IN_PROGRESS;
    }

    private GameState diaWin() {
        if ((board[0][0] == Tile.CROSS && board[1][1] == Tile.CROSS && board[2][2] == Tile.CROSS) ||
                (board[2][0] == Tile.CROSS && board[1][1] == Tile.CROSS && board[0][2] == Tile.CROSS)) {
            if (aiEnabled) {
                aiDefeated++;
                return GameState.PLAYER_ONE;
            } else {
                playerOneWins++;
                return GameState.PLAYER_ONE;
            }
        } else if ((board[0][0] == Tile.CIRCLE && board[1][1] == Tile.CIRCLE && board[2][2] == Tile.CIRCLE) ||
                (board[2][0] == Tile.CIRCLE && board[1][1] == Tile.CIRCLE && board[0][2] == Tile.CIRCLE)) {
            if (aiEnabled) {
                aiWins++;
                return GameState.PLAYER_TWO;
            } else {
                playerTwoWins++;
                return GameState.PLAYER_TWO;
            }
        }
        return GameState.IN_PROGRESS;
    }

    private GameState drawCheck() {
        for (int i=0; i<BOARD_SIZE; i++) {
            for (int j=0; j<BOARD_SIZE; j++) {
                if (board[i][j] == Tile.BLANK) {
                    return GameState.IN_PROGRESS;
                }
            }
        }
        totDraws++;
        return GameState.DRAW;
    }
}
