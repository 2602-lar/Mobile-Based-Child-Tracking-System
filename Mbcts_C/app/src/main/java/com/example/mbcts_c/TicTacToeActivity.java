package com.example.mbcts_c;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicTacToeActivity extends AppCompatActivity {

    private boolean playerXTurn = true; // True if X's turn, false if O's turn
    private int[] gameState = new int[9]; // 0 for unselected, 1 for X, 2 for O
    private int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    private TextView statusTextView;
    private Button[] buttons = new Button[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        statusTextView = findViewById(R.id.statusTextView);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int index = i;
            buttons[i] = (Button) gridLayout.getChildAt(i);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGridButtonClick(index);
                }
            });
            gameState[i] = 0;
        }
    }

    private void onGridButtonClick(int index) {
        if (gameState[index] == 0) {
            gameState[index] = playerXTurn ? 1 : 2;
            buttons[index].setText(playerXTurn ? "X" : "O");
            buttons[index].setEnabled(false);
            if (checkWin()) {
                statusTextView.setText(playerXTurn ? " player 1 : X wins!" : "player 2 : O wins!");
                disableAllButtons();
            } else if (isDraw()) {
                statusTextView.setText("It's a draw!");
            } else {
                playerXTurn = !playerXTurn;
                statusTextView.setText(playerXTurn ? "player 1 : X's turn" : "player 2 : O's turn");
            }
        }
    }

    private boolean checkWin() {
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (int state : gameState) {
            if (state == 0) {
                return false;
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }
}