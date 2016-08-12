package com.example.rodri.letsworkout.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rodri on 8/12/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "lets_workout_project.db";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_EXERCISES = "exercises";
    private static final String TABLE_MUSCLE_GROUPS = "muscle_groups";
    private static final String TABLE_EXERCISE_REPETITIONS = "exercise_repetitions";
    private static final String TABLE_BODY_MEASURES = "body_measures";
    private static final String TABLE_DAYS = "days";
    private static final String TABLE_ROUTINE = "routine";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    // muscle_groups column names = { id, name }

    // exercises column names = { id, name, muscle_group_id }
    private static final String COLUMN_MUSCLE_GROUP_ID = "muscle_group_id";

    // exercise_repetitions column names = { id, exercise_id, sets, reps }
    private static final String COLUMN_EXERCISE_ID = "exercise_id";
    private static final String COLUMN_SETS = "sets";
    private static final String COLUMN_REPS = "reps";

    // body_measures column names = { id, right_upper_arm, left_upper_arm, right_forearm,
    // left_forearm, chest, right_thigh, left_thigh, right_calf, left_calf, waist , shoulder }
    private static final String COLUMN_RIGHT_UPPER_ARM = "right_upper_arm";
    private static final String COLUMN_LEFT_UPPER_ARM = "left_upper_arm";
    private static final String COLUMN_RIGHT_FOREARM = "right_forearm";
    private static final String COLUMN_LEFT_FOREARM = "left_forearm";
    private static final String COLUMN_CHEST = "chest";
    private static final String COLUMN_RIGHT_THIGH = "right_thigh";
    private static final String COLUMN_LEFT_THIGH = "left_thigh";
    private static final String COLUMN_RIGHT_CALF = "right_calf";
    private static final String COLUMN_LEFT_CALF = "left_calf";
    private static final String COLUMN_WAIST = "waist";
    private static final String COLUMN_SHOULDER = "shoulder";

    // days column names = { id, name }

    // routine column names = { id, day_id, exercise_repetitions_id }
    private static final String COLUMN_DAY_ID = "day_id";
    private static final String COLUMN_EXERCISE_REPETITIONS_ID = "exercise_repetitions_id";


    // --- CREATE TABLES --- //

    // create table muscle_groups
    private static final String CREATE_TABLE_MUSCLE_GROUPS =
            "CREATE TABLE " + TABLE_MUSCLE_GROUPS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL);";

    // create table exercises
    private static final String CREATE_TABLE_EXERCISES =
            "CREATE TABLE " + TABLE_EXERCISES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + COLUMN_MUSCLE_GROUP_ID + " INTEGER NOT NULL, "
            + " FOREIGN KEY (" + COLUMN_MUSCLE_GROUP_ID + ") REFERENCES " + TABLE_MUSCLE_GROUPS + "(" + KEY_ID + "));";

    // create table exercise_repetitions
    private static final String CREATE_TABLE_EXERCISE_REPETITIONS =
            "CREATE TABLE " + TABLE_EXERCISE_REPETITIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXERCISE_ID + " INTEGER NOT NULL, "
            + COLUMN_SETS + " INTEGER NOT NULL, "
            + COLUMN_REPS + " INTEGER NOT NULL, "
            + " FOREIGN KEY (" + COLUMN_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISES + "(" + KEY_ID + "));";

    // create table body_measures
    private static final String CREATE_TABLE_BODY_MEASURES =
            "CREATE TABLE " + TABLE_BODY_MEASURES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RIGHT_UPPER_ARM + " REAL NOT NULL, "
            + COLUMN_LEFT_UPPER_ARM + " REAL NOT NULL, "
            + COLUMN_RIGHT_FOREARM + " REAL NOT NULL, "
            + COLUMN_LEFT_FOREARM + " REAL NOT NULL, "
            + COLUMN_CHEST + " REAL NOT NULL, "
            + COLUMN_RIGHT_THIGH + " REAL NOT NULL, "
            + COLUMN_LEFT_THIGH + " REAL NOT NULL, "
            + COLUMN_RIGHT_CALF + " REAL NOT NULL, "
            + COLUMN_LEFT_CALF + " REAL NOT NULL, "
            + COLUMN_WAIST + " REAL NOT NULL, "
            + COLUMN_SHOULDER + " REAL NOT NULL);";

    // create table days
    private static final String CREATE_TABLE_DAYS =
            "CREATE TABLE " + TABLE_DAYS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL);";

    // create table routine
    private static final String CREATE_TABLE_ROUTINE =
            "CREATE TABLE " + TABLE_ROUTINE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DAY_ID + " INTEGER NOT NULL, "
            + COLUMN_EXERCISE_REPETITIONS_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_DAY_ID + ") REFERENCES " + TABLE_DAYS + "(" + KEY_ID + "), "
            + "FOREIGN KEY (" + COLUMN_EXERCISE_REPETITIONS_ID + ") REFERENCES "
                    + TABLE_EXERCISE_REPETITIONS + "(" + KEY_ID + "));";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MUSCLE_GROUPS);
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_EXERCISE_REPETITIONS);
        db.execSQL(CREATE_TABLE_BODY_MEASURES);
        db.execSQL(CREATE_TABLE_DAYS);
        db.execSQL(CREATE_TABLE_ROUTINE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data.");
        
    }
}
