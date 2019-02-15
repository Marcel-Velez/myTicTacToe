package com.example.mytictactoe;


public class Game {
    final private int BOARD_SIZE = 3;
    private Tile[][] board;

    private Boolean playerOneTurn; // true if it is player 1's turn false if 2's turn
    private int movesPlayed;
    private Boolean gameOver;

    public Game() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i=0; i<BOARD_SIZE; i++) {
            for (int j=0; j<BOARD_SIZE; j++) {
                board[i][j] = Tile.BLANK;
            }
        }

        playerOneTurn = true;
        gameOver = false;
    }

    public Tile choose(int row, int column) {
        Tile state = board[row][column];

        if(state == Tile.BLANK) {
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
        if (rowWin() == GameState.PLAYER_ONE || rowWin() == GameState.PLAYER_TWO ) {
            return rowWin();
        } else if (colWin() == GameState.PLAYER_ONE || colWin() == GameState.PLAYER_TWO ) {
            return colWin();
        } else if (diaWin() == GameState.PLAYER_ONE || diaWin() == GameState.PLAYER_TWO ) {
            return diaWin();
        } else if (drawCheck() == GameState.IN_PROGRESS || drawCheck() == GameState.DRAW) {
            return drawCheck();
        } else {
            return GameState.IN_PROGRESS;
        }
    }

    private GameState rowWin() {
        if ((board[0][0]== Tile.CROSS && board[1][0]== Tile.CROSS && board[2][0]== Tile.CROSS) ||
                (board[0][1] == Tile.CROSS && board[1][1] == Tile.CROSS && board[2][1] == Tile.CROSS) ||
                (board[0][2] == Tile.CROSS && board[1][2] == Tile.CROSS && board[2][2] == Tile.CROSS)) {
            return GameState.PLAYER_ONE;
        } else if ((board[0][0]== Tile.CIRCLE && board[1][0]== Tile.CIRCLE && board[2][0]== Tile.CIRCLE) ||
                (board[0][1] == Tile.CIRCLE && board[1][1] == Tile.CIRCLE && board[2][1] == Tile.CIRCLE) ||
                (board[0][2] == Tile.CIRCLE && board[1][2] == Tile.CIRCLE && board[2][2] == Tile.CIRCLE)) {
            return GameState.PLAYER_TWO;
        }
        return GameState.IN_PROGRESS;
    }


    private GameState colWin() {
        if ((board[0][0]== Tile.CROSS && board[0][1]== Tile.CROSS && board[0][2]== Tile.CROSS) ||
                (board[1][0] == Tile.CROSS && board[1][1] == Tile.CROSS && board[1][2] == Tile.CROSS) ||
                (board[2][0] == Tile.CROSS && board[2][1] == Tile.CROSS && board[2][2] == Tile.CROSS)) {
            return GameState.PLAYER_ONE;
        } else if ((board[0][0]== Tile.CIRCLE && board[0][1]== Tile.CIRCLE && board[0][2]== Tile.CIRCLE) ||
                (board[1][0] == Tile.CIRCLE && board[1][1] == Tile.CIRCLE && board[1][2] == Tile.CIRCLE) ||
                (board[2][0] == Tile.CIRCLE && board[2][1] == Tile.CIRCLE && board[2][2] == Tile.CIRCLE)) {
            return GameState.PLAYER_TWO;
        }
        return GameState.IN_PROGRESS;
    }

    private GameState diaWin() {
        if ((board[0][0] == Tile.CROSS && board[1][1] == Tile.CROSS && board[2][2] == Tile.CROSS) ||
                (board[2][0] == Tile.CROSS && board[1][1] == Tile.CROSS && board[0][2] == Tile.CROSS)) {
            return GameState.PLAYER_ONE;
        } else if ((board[0][0] == Tile.CIRCLE && board[1][1] == Tile.CIRCLE && board[2][2] == Tile.CIRCLE) ||
                (board[2][0] == Tile.CIRCLE && board[1][1] == Tile.CIRCLE && board[0][2] == Tile.CIRCLE)) {
            return GameState.PLAYER_TWO;
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
        return GameState.DRAW;
    }
}
