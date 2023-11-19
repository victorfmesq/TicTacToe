package com.example.jogodavelha.data;

public class ScriptDLL {
    public static String getCreateTableTicTacToe() {
        return "CREATE TABLE IF NOT EXISTS TicTacToe (" +
                "ID INTEGER PRIMARY KEY," +
                "oVictories INTEGER DEFAULT 0," +
                "xVictories INTEGER DEFAULT 0," +
                "lastWinner TEXT CHECK (LENGTH(lastWinner) <= 1) DEFAULT 'X');";
    }

    public static String getScoreBoard(){
        StringBuilder sql = new StringBuilder();
        sql.append("Select ID,oVictories,xVictories,lastWinner");
        sql.append(" from TicTacToe");
        return sql.toString();
    }
}
