package com.example.mytictactoe;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Game game;
    boolean aiEnabled = false;

    static HashMap<String, int[]> B2C = createMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            game = (Game) savedInstanceState.get("game");
            loadGame();
        } else {
            game = new Game();
        }
    }

    private void loadGame() {
        Tile[][] board = game.getBoard();
        String text;
        TextView mainText = (TextView) getView("mainText");
        if (game.stillInProgress()) {
            text = game.onesTurn() ? "Player 1's Turn" : "Player 2's Turn";
        } else {
            text = game.onesTurn() ? "PLAYER 2 WON!" : "PLAYER 1 WON!";
            unableButtons();
        }
        mainText.setText(text);

        for (int i = 0; i < game.getBOARD_SIZE(); i++) {
            for (int j = 0; j < game.getBOARD_SIZE(); j++) {
                Button clicked = (Button) getView("button" + Integer.toString(i*3+j));
                if (board[j][i] == Tile.CROSS) {
                    clicked.setText("X");
                    clicked.setTextColor(Color.parseColor("#0066ff"));
                } else if (board[j][i] == Tile.CIRCLE) {
                    clicked.setText("O");
                    clicked.setTextColor(Color.parseColor("#ff0000"));
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("game", game);
    }

    public View getView(String name) {
        int resID = getResources().getIdentifier(name, "id", getPackageName());
        return findViewById(resID);
    }

    private static HashMap<String, int[]> createMap() {
        HashMap<String, int[]> nameToCoord = new HashMap<String, int[]>();
        nameToCoord.put("button0", new int[]{0,0});
        nameToCoord.put("button1", new int[]{1,0});
        nameToCoord.put("button2", new int[]{2,0});
        nameToCoord.put("button3", new int[]{0,1});
        nameToCoord.put("button4", new int[]{1,1});
        nameToCoord.put("button5", new int[]{2,1});
        nameToCoord.put("button6", new int[]{0,2});
        nameToCoord.put("button7", new int[]{1,2});
        nameToCoord.put("button8", new int[]{2,2});
        return nameToCoord;
    }

    public void tileClicked(View view) {
        if (game.stillInProgress()) {
            Tile state;

            TextView mainText = (TextView) getView("mainText");

            int[] coord = B2C.get((String) view.getTag());
            state = game.choose(coord[0], coord[1]);

            Button clicked = (Button) view;
            switch (state) {
                case CROSS:
                    clicked.setText("X");
                    mainText.setText("Player 2's Turn");
                    clicked.setTextColor(Color.parseColor("#0066ff"));
                    break;
                case CIRCLE:
                    clicked.setText("O");
                    mainText.setText("Player 1's Turn");
                    clicked.setTextColor(Color.parseColor("#ff0000"));
                    break;
                case INVALID:
                    mainText.setText(mainText.getText() + ", not that tile!");
                    break;
            }
        }
        winCheck();
        aiTurn();
    }

    private void aiTurn() {
        if (game.aiTurn() && aiEnabled && game.stillInProgress()) {
            View button = getView(game.aiMove());
            tileClicked(button);
        }
    }

    private void winCheck() {
        GameState state = game.won();
        if (!game.stillInProgress()) {
            unableButtons();
            TextView leText = (TextView) getView("mainText");
            if  (state == GameState.DRAW) {
                leText.setText("DRAW");
            } else if (state == GameState.PLAYER_ONE) {
                leText.setText("PLAYER ONE WON!");
            } else if (state == GameState.PLAYER_TWO) {
                leText.setText("PLAYER TWO WON!");
            }
        }
    }

    private void unableButtons() {
        android.support.v7.widget.GridLayout mlayout;
        int count, i;

        mlayout= (android.support.v7.widget.GridLayout) getView("GridLayout");

        count = mlayout.getChildCount();
        for(i = 0 ; i < count ; i++) {
            Button button = (Button) mlayout.getChildAt(i);
            button.setEnabled(false);
        }
    }

    public void resetClicked(View view) {
        android.support.v7.widget.GridLayout mlayout;
        TextView mainText;
        int count, i;

        game = new Game();
        if (aiEnabled) {
            game.switchAI();
        }

        mlayout = (android.support.v7.widget.GridLayout) getView("GridLayout");
        mainText = (TextView) getView("mainText");

        count = mlayout.getChildCount();
        for(i = 0 ; i < count ; i++){
            Button button = (Button) mlayout.getChildAt(i);
            button.setText("");
            button.setEnabled(true);
        }
        mainText.setText("Player 1's Turn");
    }

    // https://www.tutorialspoint.com/how-do-i-display-an-alert-dialog-on-android
    private void alertDialog(String title, String message) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void showStats(View view) {
        HashMap<String,Integer> stats = Game.retrieveStats();
        String drawInfo = "Number of draws: " + stats.get("total draws") + '\n';
        drawInfo += "Games played: " + stats.get("total games") + '\n';
        drawInfo += "Moves played: " + stats.get("total moves") + '\n';
        drawInfo += "Player one wins: " + stats.get("player one wins") + '\n';
        drawInfo += "Player two wins: " + stats.get("player two wins") + '\n';
        drawInfo += "Lost to ai: " + stats.get("ai wins") + '\n';
        drawInfo += "A.I. defeated: " + stats.get("ai defeated") + '\n';
        drawInfo += "A.I. enabled: " + Boolean.toString(game.aiEnabled) + '\n';
        alertDialog("Statistics",drawInfo);
    }


    public void switchAI(View view) {
        Button switchButton = (Button) getView("aiButton");
        game.switchAI();
        if (aiEnabled) {
            switchButton.setText("Battle AI");
            aiEnabled = false;
        } else {
            switchButton.setText("VS a friend");
            aiEnabled = true;
        }

    }
}
