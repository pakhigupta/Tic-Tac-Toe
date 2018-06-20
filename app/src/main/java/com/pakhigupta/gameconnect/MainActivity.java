package com.pakhigupta.gameconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0 = X ; 1 = O
    int activePlayer = 0;

    //-1 means unplayed; 0/1 means played by green/red
    int[] gameState = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int chancePlayed = 0;

    boolean gameIsActive = true;
    int[][] winningPositions = { {0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == -1 && gameIsActive) {

            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = activePlayer;
            chancePlayed ++;
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.x);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.o);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(300);

            //checking winning poitions
            for(int[] winningPosition: winningPositions) {
                if (gameState[winningPosition[0]] != -1 &&
                        gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]]) {
                    //Toast.makeText(MainActivity.this, activePlayer + "Won!", Toast.LENGTH_SHORT).show();

                    String winner = "";
                    if (activePlayer == 0) {
                        winner = "O";
                    } else {
                        winner = "X";
                    }
                    gameIsActive = false;
                    //Toast.makeText(MainActivity.this, winner + " won!" , Toast.LENGTH_SHORT).show();
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " Won!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }

               /* else {
                    boolean gameIsOver = true;

                    for(int counterState : gameState) {
                        if(counterState == -1) gameIsOver = false;
                    }

                    if(gameIsOver) {

                        TextView winnerMessage =  (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a Draw!");
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);

                    }
                    }//end of win check if else */

            boolean gameIsOver = false;
            if (chancePlayed == 9 && gameIsActive) {
                gameIsOver = true;

            }
            if(gameIsOver) {

                TextView winnerMessage =  (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText("It's a Draw!");
                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);
                gameIsActive = false;

            }
        }
        else {
            Toast.makeText(MainActivity.this, "Move not allowed" , Toast.LENGTH_SHORT).show();
        }
    }

    public void playAgain(View view) {

        gameIsActive = true;

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        //0 = O ; 1 = X
        activePlayer = 0;
        chancePlayed = 0;
        //-1 means unplayed; 0/1 means played by green/red
        for(int i=0; i<gameState.length; i++) {
            gameState[i] = -1;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
