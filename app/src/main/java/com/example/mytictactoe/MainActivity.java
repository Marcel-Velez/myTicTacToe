package com.example.mytictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();
    }

    private int[] getCoord(View view) {
        String id = (String) view.getTag();

        switch (id) {
            case "button0": int[] a = {0,0};
                            return a;

            case "button1": int[] b = {1,0};
                            return b;

            case "button2": int[] c = {2,0};
                            return c;

            case "button3": int[] d = {0,1};
                            return d;

            case "button4": int[] e = {1,1};
                            return e;

            case "button5": int[] f = {2,1};
                            return f;

            case "button6": int[] g = {0,2};
                            return g;

            case "button7": int[] h = {1,2};
                            return h;

            case "button8": int[] i = {2,2};
                            return i;

        }
        int[] exception = {0,0};
        return exception;
    }

    public void tileClicked(View view) {
        if (game.won() == GameState.IN_PROGRESS) {
            Tile state;

            int resID = getResources().getIdentifier("mainText", "id", getPackageName());
            TextView maintText = (TextView) findViewById(resID);

            int[] coord = getCoord(view);
            state = game.choose(coord[0], coord[1]);

            Button clicked = (Button) view;
            switch (state) {
                case CROSS:
                    clicked.setText("X");
                    maintText.setText("Player 2's Turn");
                    //clicked.setTextColor(Color.holo_purple);
                    break;
                case CIRCLE:
                    clicked.setText("O");
                    maintText.setText("Player 1's Turn");
                    //clicked.setTextColor(getApplication().getResources().getColor(R.color.red));
                    break;
                case INVALID:   //something;
                    break;
            }
        }
        if (game.won() != GameState.IN_PROGRESS) {
            unableButtons();
            int resID = getResources().getIdentifier("button4", "id", getPackageName());
            Button leButton = (Button) findViewById(resID);
            GameState state = game.won();
            if  (state == GameState.DRAW) {
                leButton.setText("DRAW");
            } else if (state == GameState.PLAYER_ONE) {
                leButton.setText("PLAYER ONE WON!");
            } else if (state == GameState.PLAYER_TWO) {
                leButton.setText("PLAYER TWO WON!");
            }
        }
    }

    private void unableButtons() {
        int resID = getResources().getIdentifier("GridLayout", "id", getPackageName());
        android.support.v7.widget.GridLayout mlayout = (android.support.v7.widget.GridLayout) findViewById(resID);

        int count = mlayout.getChildCount();
        for(int i = 0 ; i < count ; i++) {
            Button button = (Button) mlayout.getChildAt(i);
            button.setEnabled(false);
        }
    }

    public void resetClicked(View view) {
        game = new Game();

        int resID = getResources().getIdentifier("GridLayout", "id", getPackageName());
        android.support.v7.widget.GridLayout mlayout = (android.support.v7.widget.GridLayout) findViewById(resID);

        int count = mlayout.getChildCount();
        for(int i = 0 ; i < count ; i++){
            Button button = (Button) mlayout.getChildAt(i);
            button.setText("");
            button.setEnabled(true);
        }

    }


}
