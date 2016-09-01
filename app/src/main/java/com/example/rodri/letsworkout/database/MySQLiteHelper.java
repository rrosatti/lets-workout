package com.example.rodri.letsworkout.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rodri.letsworkout.R;

/**
 * Created by rodri on 8/12/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Arrays that will get data trough getResources()
    private String[] muscleGroups;
    private String[] days;
    private String[] chestExercises;

    // Database name
    private static final String DATABASE_NAME = "lets_workout_project.db";

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Table Names
    public static final String TABLE_EXERCISE = "exercise";
    public static final String TABLE_MUSCLE_GROUP = "muscle_group";
    public static final String TABLE_EXERCISE_REPETITION = "exercise_repetition";
    public static final String TABLE_BODY_MEASURE = "body_measure";
    public static final String TABLE_DAYS = "days";
    public static final String TABLE_ROUTINE = "routine";
    public static final String TABLE_USER = "user";
    public static final String TABLE_USER_BODY = "user_body";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    // muscle_group column names = { id, name }

    // exercise column names = { id, name, muscle_group_id }
    public static final String COLUMN_MUSCLE_GROUP_ID = "muscle_group_id";

    // exercise_repetition column names = { id, exercise_id, sets, reps }
    public static final String COLUMN_EXERCISE_ID = "exercise_id";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";

    // body_measure column names = { id, right_upper_arm, left_upper_arm, right_forearm, left_forearm,
    // chest, right_thigh, left_thigh, right_calf, left_calf, waist , shoulder, weight, height, date }
    public static final String COLUMN_RIGHT_UPPER_ARM = "right_upper_arm";
    public static final String COLUMN_LEFT_UPPER_ARM = "left_upper_arm";
    public static final String COLUMN_RIGHT_FOREARM = "right_forearm";
    public static final String COLUMN_LEFT_FOREARM = "left_forearm";
    public static final String COLUMN_CHEST = "chest";
    public static final String COLUMN_RIGHT_THIGH = "right_thigh";
    public static final String COLUMN_LEFT_THIGH = "left_thigh";
    public static final String COLUMN_RIGHT_CALF = "right_calf";
    public static final String COLUMN_LEFT_CALF = "left_calf";
    public static final String COLUMN_WAIST = "waist";
    public static final String COLUMN_SHOULDER = "shoulder";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_DATE = "date";

    // days column names = { id, name }

    // routine column names = { id, day_id, exercise_repetitions_id }
    public static final String COLUMN_DAY_ID = "day_id";
    public static final String COLUMN_EXERCISE_REPETITIONS_ID = "exercise_repetitions_id";

    // user column names = { id, name, login, password }
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";

    // user_body column names = { id, user_id, body_measures_id }
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_BODY_MEASURES_ID = "body_measures_id";


    // --- CREATE TABLES --- //

    // create table muscle_groups
    private static final String CREATE_TABLE_MUSCLE_GROUP =
            "CREATE TABLE " + TABLE_MUSCLE_GROUP + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL);";

    // create table exercises
    private static final String CREATE_TABLE_EXERCISE =
            "CREATE TABLE " + TABLE_EXERCISE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + COLUMN_MUSCLE_GROUP_ID + " INTEGER NOT NULL, "
            + " FOREIGN KEY (" + COLUMN_MUSCLE_GROUP_ID + ") REFERENCES " + TABLE_MUSCLE_GROUP + "(" + KEY_ID + "));";

    // create table exercise_repetitions
    private static final String CREATE_TABLE_EXERCISE_REPETITION =
            "CREATE TABLE " + TABLE_EXERCISE_REPETITION + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXERCISE_ID + " INTEGER NOT NULL, "
            + COLUMN_SETS + " INTEGER NOT NULL, "
            + COLUMN_REPS + " INTEGER NOT NULL, "
            + " FOREIGN KEY (" + COLUMN_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISE + "(" + KEY_ID + "));";

    // create table body_measures
    private static final String CREATE_TABLE_BODY_MEASURE =
            "CREATE TABLE " + TABLE_BODY_MEASURE + "("
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
            + COLUMN_SHOULDER + " REAL NOT NULL, "
            + COLUMN_WEIGHT + " REAL NOT NULL, "
            + COLUMN_HEIGHT + " REAL NOT NULL, "
            + COLUMN_DATE + " INTEGER NOT NULL);";


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
                    + TABLE_EXERCISE_REPETITION + "(" + KEY_ID + "));";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + COLUMN_LOGIN + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_USER_BODY =
            "CREATE TABLE " + TABLE_USER_BODY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_BODY_MEASURES_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + KEY_ID + "), "
            + "FOREIGN KEY (" + COLUMN_BODY_MEASURES_ID + ") REFERENCES " + TABLE_BODY_MEASURE + "(" + KEY_ID + "));";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        muscleGroups = context.getResources().getStringArray(R.array.muscle_groups);
        days = context.getResources().getStringArray(R.array.days);
        chestExercises = context.getResources().getStringArray(R.array.chest_exercises);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MUSCLE_GROUP);
        db.execSQL(CREATE_TABLE_EXERCISE);
        db.execSQL(CREATE_TABLE_EXERCISE_REPETITION);
        db.execSQL(CREATE_TABLE_BODY_MEASURE);
        db.execSQL(CREATE_TABLE_DAYS);
        db.execSQL(CREATE_TABLE_ROUTINE);
        populateTableMuscleGroup(db);
        populateTableDays(db);
        // added after version 2
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_USER_BODY);
        // added after version 3
        populateTableExercises(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data.");

        if (newVersion > oldVersion) {
            System.out.println("The database was updated!");

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSCLE_GROUP);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE_REPETITION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BODY_MEASURE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_BODY);

            db.execSQL(CREATE_TABLE_MUSCLE_GROUP);
            db.execSQL(CREATE_TABLE_EXERCISE);
            db.execSQL(CREATE_TABLE_EXERCISE_REPETITION);
            db.execSQL(CREATE_TABLE_BODY_MEASURE);
            db.execSQL(CREATE_TABLE_DAYS);
            db.execSQL(CREATE_TABLE_ROUTINE);
            populateTableMuscleGroup(db);
            populateTableDays(db);
            // add after version 2
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_USER_BODY);
        }

    }

    /**
     *
     * Populate the muscle_groups table with the values found in strings.xml
     *
     * @param db
     */
    public void populateTableMuscleGroup(SQLiteDatabase db) {
        for (int i = 0; i < muscleGroups.length; i++) {
            db.execSQL("INSERT INTO " + TABLE_MUSCLE_GROUP + " (" + KEY_NAME + ") " +
                    " VALUES('" + muscleGroups[i] + "');");
        }
    }

    /**
     * Populate the days table with the values found in strings.xml
     *
     * @param db
     */
    public void populateTableDays(SQLiteDatabase db) {
        for (int i = 0; i < days.length; i++) {
            db.execSQL("INSERT INTO " + TABLE_DAYS + " (" + KEY_NAME + ")" +
            "VALUES('" + days[i] + "');");
        }
    }

    public void populateTableExercises(SQLiteDatabase db) {
        for (int i = 0; i < chestExercises.length; i++) {
            db.execSQL("INSERT INTO " + TABLE_EXERCISE + " (" + KEY_NAME + ", " + COLUMN_MUSCLE_GROUP_ID + ") " +
            "VALUES('" + muscleGroups[i] + "', 1);");
        }
    }
}
