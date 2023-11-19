package com.example.jogodavelha.model;

import android.util.Log;

public class ScoreBoard {
    private int id;
    private int oVictories;
    private int xVictories;
    private String lastWinner;

    public ScoreBoard() {
        this.id = 0;
        this.oVictories = 0;
        this.xVictories = 0;
        this.lastWinner = "";
    }

    public ScoreBoard(int oVictories, int xVictories, String lastWinner) {
        this.id = 0;
        this.oVictories = oVictories;
        this.xVictories = xVictories;
        this.lastWinner = lastWinner;
    }

    public ScoreBoard(ScoreBoard scoreBoard) {
        if (scoreBoard != null) {
            this.id = scoreBoard.id;
            this.oVictories = scoreBoard.oVictories;
            this.xVictories = scoreBoard.xVictories;
            this.lastWinner = scoreBoard.lastWinner;
        } else {
            Log.e("ScoreBoard", "Scoreboard cannot be null: " + scoreBoard);

            throw new IllegalArgumentException("scoreBoard parameter cannot be null");
            // Alternatively, you can set default values or handle it in a way that makes sense for your application.
        }
    }

    // Getters and setters for each field

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOVictories() {
        return oVictories;
    }

    public void setOVictories(int oVictories) {
        this.oVictories = oVictories;
    }

    public int getXVictories() {
        return xVictories;
    }

    public void setXVictories(int xVictories) {
        this.xVictories = xVictories;
    }

    public String getLastWinner() {
        return lastWinner;
    }

    public void setLastWinner(String lastWinner) {
        this.lastWinner = lastWinner;
    }

    public void increaseXVictories() {
        this.xVictories += 1;
    }

    public void increaseOVictories() {
        this.oVictories += 1;
    }

    @Override
    public String toString() {
        return "ScoreBoard{" +
                "id=" + id +
                ", oVictories=" + oVictories +
                ", xVictories=" + xVictories +
                ", lastWinner='" + lastWinner + '\'' +
                '}';
    }
}
