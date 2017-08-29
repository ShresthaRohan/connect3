package com.rohanshrestha.connect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; //0 for yellow, 1 for red
    boolean gameIsActive = true;

    int[] gameState = {2,2,2,2,2,2,2,2,2}; // 2 denotes unplayed slot in the board
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    public void dropIn (View view) {

        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;

            } else {

                counter.setImageResource(R.drawable.red);
                activePlayer = 0;

            }

            counter.animate().translationYBy(1000f).setDuration(300);

            //loop through the elements inside winningPositions and during each instance of loop,
            //the array winningState is equal to the array in winningPositions at that instance
            //eg. during 2nd loop instance, winningState = {6,7,8} = winningPositions[2][]
            //whenever a move is made, this checks if a winning condition has been met
            for(int [] winningState : winningPositions){

                //check that whether the gameState's(i.e. which player is active on the denoted position) of counter in positions denoted by
                //the elements in array winningState are equal and not equal to 2 (i.e. selected)
                // eg. when winningState = {6,7,8}, check if gameState[6]==gameState[7]==gameState[8]!=2
                if(gameState[winningState[0]] == gameState[winningState[1]] &&
                        gameState[winningState[1]] == gameState[winningState[2]] &&
                        gameState[winningState[0]] != 2){

                    //System.out.println(gameState[winningState[0]]);//prints 0 or 1 to console because if the condition is true, the checked gameState's are either 1 or 2

                    gameIsActive = false; //so that no move can be made after someone wins

                    //to display who has won
                    LinearLayout wonLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    String winner = "Red";
                    wonLayout.setBackgroundColor(Color.RED);//Change color of layout
                    if (gameState[winningState[0]] == 0){

                        winner = "Yellow";
                        wonLayout.setBackgroundColor(Color.YELLOW);

                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                    winnerMessage.setText(winner + " has won!");

                    wonLayout.setVisibility(View.VISIBLE);//make the win dialog appear

                } else {

                    boolean gameIsOver = true;

                    for(int counterState : gameState) {

                        if(counterState == 2) gameIsOver = false;

                    }

                    //to make sure that the game does not declare draw if someone wins on the last move
                    if(gameIsOver && gameIsActive) {

                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw!");

                        LinearLayout wonLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        wonLayout.setVisibility(View.VISIBLE);

                    }

                }


            }
        }
    }

    public void playAgain (View view) {

        activePlayer = 0;
        gameIsActive = true;

        LinearLayout wonLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
        wonLayout.setVisibility(View.INVISIBLE);

        //clear the game states
        for(int i = 0; i<gameState.length; i++) {

            gameState[i]=2;

        }

        //clear the board
        GridLayout gridBoard = (GridLayout) findViewById(R.id.gridBoard);
        for(int i = 0; i<gridBoard.getChildCount(); i++) {

            ((ImageView)gridBoard.getChildAt(i)).setImageResource(0);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
