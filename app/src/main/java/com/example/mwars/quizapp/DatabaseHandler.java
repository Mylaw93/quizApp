package com.example.mwars.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mwars on 22.01.2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "questionManager";

    private static final String TABLE_QUESTION = "questions";

    private static final String KEY_ID = "id";
    private static final String KEY_QUEST = "question";
    private static final String KEY_ANSW = "answers";
    private static final String KEY_CORR_ANSW = "correctAnswer";


    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTION + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_QUEST + " TEXT," +
                KEY_ANSW + " TEXT," +
                KEY_CORR_ANSW + " TEXT" + ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);

        onCreate(db);
    }


    public void addQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_QUEST, question.getQuestion());
        values.put(KEY_ANSW, question.getAnswers().toString());
        values.put(KEY_CORR_ANSW, question.getCorrectAnswer());

        db.insert(TABLE_QUESTION, null, values);
        db.close();
    }


    public Question getQuestion(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTION, new String[] {KEY_ID, KEY_QUEST, KEY_ANSW, KEY_CORR_ANSW}, KEY_ID + "=?",
                                                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Question question = new Question(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                Arrays.asList(cursor.getString(2).split("\\s*,\\s*")));
//                , String.valueOf(cursor.getString(3)));

        return question;
    }


    public List<Question> getAllQuestions(){
        List<Question> questionsList = new ArrayList<Question>();

        String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_QUESTION;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(SELECT_ALL_QUERY, null);

        if(cursor.moveToFirst()){
            do{
                Question quest = new Question();
                quest.setId(Integer.parseInt(cursor.getString(0)));
                quest.setQuestion(cursor.getString(1));
                quest.setAnswers(Arrays.asList(cursor.getString(2).split("\\s*,\\s*")));

                questionsList.add(quest);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return questionsList;
    }


    public int getQuestionsCount(){
        String COUNT_QUERY = "SELECT * FROM " + TABLE_QUESTION;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(COUNT_QUERY, null);
        cursor.close();

        return cursor.getCount();
    }


    public int updateQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_QUEST, question.getQuestion());
        values.put(KEY_ANSW, question.getAnswers().toString());
        values.put(KEY_CORR_ANSW, question.getCorrectAnswer());

        db.update(TABLE_QUESTION, values, KEY_ID + " = ? ", new String[] { String.valueOf(question.getId()) });

        return question.getId();
    }


    public void deleteQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_QUESTION, KEY_ID + " = ? ", new String[] { String.valueOf(question.getId())});

        db.close();

    }



}
