package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jogodavelha.data.DatabaseHelper;
import com.example.jogodavelha.model.ScoreBoard;

public class MainActivity extends AppCompatActivity {
    private TextView oVictoriesTextView;
    private TextView xVictoriesTextView;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureScoreboardUI();

        createConnection();

        configurePlayButton();

        configureResetScoreboardButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        handleInitScoreboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    private void configureScoreboardUI() {
        oVictoriesTextView = findViewById(R.id.o_victories_count);
        xVictoriesTextView = findViewById(R.id.x_victories_count);
    }

    private void configurePlayButton() {
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(v -> startGame());
    }

    private void configureResetScoreboardButton() {
        Button resetScoreboardButton = findViewById(R.id.ResetScoreboard);
        resetScoreboardButton.setOnClickListener(v -> resetScoreboard());
    }

    private void updateScoreboardUI(ScoreBoard sb) {
        oVictoriesTextView.setText(String.valueOf(sb.getOVictories()));
        xVictoriesTextView.setText(String.valueOf(sb.getXVictories()));
    }


    private void startGame() {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    private void resetScoreboard() {
        ScoreBoard sb = databaseHelper.getScoreBoard();

        if (sb != null) {
            sb.setOVictories(0);
            sb.setXVictories(0);
            databaseHelper.alter(sb);
            updateScoreboardUI(sb);
        }
    }

    private void handleInitScoreboard() {
        configureScoreboardUI();

        ScoreBoard sb = databaseHelper.getScoreBoard();

        if(sb == null){
            sb = new ScoreBoard();
        }

        updateScoreboardUI(sb);
    }

    private void createConnection(){
        try{
            databaseHelper = new DatabaseHelper(this.getApplicationContext());
            database = databaseHelper.getWritableDatabase();

            Toast.makeText(this,"Conexão bem sucedida",Toast.LENGTH_LONG).show();
        }catch(SQLException e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

}
