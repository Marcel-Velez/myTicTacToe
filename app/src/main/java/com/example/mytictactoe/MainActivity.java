package com.example.mytictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();
    }

    public void tileClicked(View view) {
        int id, row, column;
        Tile state;


        id = view.getId();
        row = (int) (id / 3);
        column = id % 3;

        state = game.choose(row, column);

        switch(state) {
            case CROSS:     //blabla;
                            break;
            case CIRCLE     //something;
                            break;
            case INVALID    //something;
                            break;
        }
    }

    public void resetClicked() {
        game = new Game();

    }


}
