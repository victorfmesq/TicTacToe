package com.example.jogodavelha;

import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jogodavelha.data.DatabaseHelper;
import com.example.jogodavelha.model.ScoreBoard;
import com.example.jogodavelha.model.VictoryCombinations;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private final char NULL = '-';
    private final char X = 'X';
    private final char O = 'O';
    private ScoreBoard scoreBoard;

    private List<Integer> oCombinations;
    private List<Integer> xCombinations;
    private boolean isXTurn;
    private TextView turnLabel;
    private Button restartButton;
    private ImageButton[] buttons;
    private char[] filledButtons = {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL};

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        dbHelper = new DatabaseHelper(this);

        oCombinations = new ArrayList<>();
        xCombinations = new ArrayList<>();

        turnLabel = findViewById(R.id.turn_label);
        scoreBoard = dbHelper.getScoreBoard();

        if(scoreBoard == null) scoreBoard = new ScoreBoard();

        buttons = new ImageButton[]{
                findViewById(R.id.ttt_button_1),
                findViewById(R.id.ttt_button_2),
                findViewById(R.id.ttt_button_3),
                findViewById(R.id.ttt_button_4),
                findViewById(R.id.ttt_button_5),
                findViewById(R.id.ttt_button_6),
                findViewById(R.id.ttt_button_7),
                findViewById(R.id.ttt_button_8),
                findViewById(R.id.ttt_button_9)
        };

        restartButton = findViewById(R.id.restartButton);
        restartButton.setClickable(false);

        isXTurn = String.valueOf(X).equals(scoreBoard.getLastWinner());

        turnLabel.append(isXTurn ? String.valueOf(X) : String.valueOf(O));

        onRestartGame();

        onClickOptionButton();
        onClickRestartButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        onClickOptionButton();
        onClickRestartButton();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(dbHelper.getScoreBoard() == null) dbHelper.insert(scoreBoard);
        else dbHelper.alter(scoreBoard);
    }

    private void updateTurnLabel(char playerChar){
        String label = turnLabel.getText().toString();
        label = label.substring(0, label.length() -1) + playerChar;

        turnLabel.setText(label);
    }
    private void  onClickRestartButton(){
        restartButton.setOnClickListener(v -> onRestartGame());
    }
    private void onRestartGame() {
        for(ImageButton button : buttons){
            button.setImageResource(android.R.drawable.screen_background_light);
            button.setBackgroundResource(android.R.drawable.btn_default);
            button.setClickable(true);
        }

        for(int i = 0; i < filledButtons.length; i++){
            filledButtons[i] = NULL;
        }

        restartButton.setClickable(false);

        onClickOptionButton();
    }
    private void onSelectOption(int buttonIndex) {
        final char turnOption = isXTurn ? X : O;
        final int turnIconId = isXTurn ? R.drawable.x_medium_icon : R.drawable.o_medium_icon;

        final boolean isOptionEmpty = filledButtons[buttonIndex] == NULL;

        if(isOptionEmpty){
            buttons[buttonIndex].setImageResource(turnIconId);
            buttons[buttonIndex].setClickable(false);

            filledButtons[buttonIndex] = turnOption;

            if(verifyVictory(turnOption))
                handlePlayerVictory(turnOption);
            else if (verifyDraw())
                handleDraw();
            else
                handleNextTurn();
        }
    }

    private void handleNextTurn(){
        isXTurn = !isXTurn;
        updateTurnLabel(isXTurn ? X : O);
    }

    private void handleDraw(){
        Toast.makeText(this,"Partida encerrada: EMPATE" ,Toast.LENGTH_LONG).show();
        restartButton.setClickable(true);
    }

    private void handlePlayerVictory(char winner) {
        Toast.makeText(this,"Partida encerrada. Vencerdor: " + winner,Toast.LENGTH_LONG).show();

        for(ImageButton button : buttons){
            button.setClickable(false);
        }

        restartButton.setClickable(true);

        if(winner == X) scoreBoard.increaseXVictories();
        else scoreBoard.increaseOVictories();


        String lastWinner = winner == X ? String.valueOf(X) : String.valueOf(O);
        scoreBoard.setLastWinner(lastWinner);
    }

    private void onClickOptionButton() {
        for(int i = 0; i < buttons.length; i++){
            final int index = i;

            buttons[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectOption(index);
                }
            });
        }
    }

    private boolean verifyDraw(){
        boolean isDraw = true;

        for(int i = 0; i < filledButtons.length; i++){
            if(filledButtons[i] == NULL){
                isDraw = false;
                break;
            }
        }

        return isDraw;
    }

    private boolean verifyVictory(char turnPlayer){
        List<Integer> playerCombinations = new ArrayList<>();

        for(int buttonIndex = 0; buttonIndex < filledButtons.length; buttonIndex++){
            if(filledButtons[buttonIndex] == turnPlayer)
                playerCombinations.add(buttonIndex);
        }

        List<Integer> victoryCombination = VictoryCombinations
                .getWinningCombination(playerCombinations);

        boolean currentPlayerWins = !victoryCombination.isEmpty();

        if(currentPlayerWins)
            handleShowWinnerRow(victoryCombination);


        return currentPlayerWins;
    }

    private void handleShowWinnerRow(List<Integer> victoryCombination){
        for(Integer index : victoryCombination){
            ImageButton filledButton = buttons[index];
            filledButton.setBackgroundColor(Color.GREEN);
        }
    }
}
