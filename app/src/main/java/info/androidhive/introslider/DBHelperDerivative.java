package info.androidhive.introslider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURAKHMET on 11.04.2017.
 */

public class DBHelperDerivative extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Derivative";
    // tasks table name
    private static final String TABLE_QUEST = "derivative_table";
    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc"; //option c
    private static final String KEY_OPTD= "optd"; //option d
    private SQLiteDatabase dbase;
    public DBHelperDerivative(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
                +KEY_OPTB +" TEXT, "+ KEY_OPTC +" TEXT, "+ KEY_OPTD +" TEXT)";
        db.execSQL(sql);
        //      addQuestions();
//db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
// Create tables again
        onCreate(db);
    }
    // Adding new question
    void addQuestion(String question,String correct,String opta,String optb,String optc,String optd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, question);
        values.put(KEY_ANSWER, correct);
        values.put(KEY_OPTA, opta);
        values.put(KEY_OPTB, optb);
        values.put(KEY_OPTC, optc);
        values.put(KEY_OPTD, optd);
// Inserting Row
        db.insert(TABLE_QUEST, null, values);

    }
    public List<Derivative_question> getAllQuestions() {
        List<Derivative_question> quesList = new ArrayList<Derivative_question>();
// Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Derivative_question quest = new Derivative_question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quest.setOPTD(cursor.getString(6));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
// return quest list
        return quesList;
    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }

    void DeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_CONTACTS ,null,null);
        db.execSQL("delete  from  "+ TABLE_QUEST + " where " + KEY_ID + ">" +"10;");
        //db.execSQL("TRUNCATE table" + TABLE_NAME);
        db.close();
    }
}
