package com.example.jogodavelha.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jogodavelha.model.ScoreBoard;

// TODO: Verificar pq os dados não estão sendo salvos no banco ou não estão vindo na consulta

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "tictactoe.db";
    private static final int VERSION = 4;

    //constructor
    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Data", "Iniciou banco");
        db.execSQL(ScriptDLL.getCreateTableTicTacToe());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    }

    public void insert(ScoreBoard sb){
        Log.i("Data", "Insert DB >> " + sb.toString());

        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("oVictories",sb.getOVictories());
        contentValues.put("xVictories",sb.getXVictories());
        contentValues.put("lastWinner",sb.getLastWinner());

        long insertId = database.insertOrThrow("TicTacToe",null, contentValues);

        Log.i("Data", "Created ROW ID " + insertId);
    }

    public void remove(int id){
        SQLiteDatabase database = this.getReadableDatabase();

        String[] params = new String[1];
        params[0] = String.valueOf(id);

        database.delete("TicTacToe","ID = ?",params);
    }

    public void alter(ScoreBoard sb){
        Log.i("Data", "Alter DB >> " + sb.toString());
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("oVictories",sb.getOVictories());
        contentValues.put("xVictories",sb.getXVictories());
        contentValues.put("lastWinner",sb.getLastWinner());

        String[] params = new String[1];
        params[0] = String.valueOf(sb.getId());

        int result = database.update("TicTacToe",contentValues,"ID = ?",params);

        Log.i("Data", "ALTER DB Result: " + result);
    }
    public ScoreBoard getScoreBoard(){
        Log.i("Data", "PUXANDO do Banco");
        SQLiteDatabase database = this.getReadableDatabase();

        ScoreBoard scoreBoard = new ScoreBoard();

        Cursor result = database.rawQuery(ScriptDLL.getScoreBoard(), null);

        if(result.getCount() > 0){
            result.moveToFirst();

            scoreBoard.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
            scoreBoard.setOVictories(result.getInt(result.getColumnIndexOrThrow("oVictories")));
            scoreBoard.setXVictories(result.getInt(result.getColumnIndexOrThrow("xVictories")));
            scoreBoard.setLastWinner(result.getString(result.getColumnIndexOrThrow("lastWinner")));

            Log.i("Data", "Achou ScoreBoard >> " + scoreBoard.toString());

            return scoreBoard;

        }

        Log.i("Data", "Não achou ScoreBoard >> " + scoreBoard.toString());


        return null;
    }
}
