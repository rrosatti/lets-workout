package com.example.rodri.letsworkout.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rodri on 8/12/2016.
 */
public class MyDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] muscleGroupsColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME
    };
    private String[] exercisesColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME,
            MySQLiteHelper.COLUMN_MUSCLE_GROUP_ID
    };
    private String[] exerciseRepetitionsColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_EXERCISE_ID,
            MySQLiteHelper.COLUMN_SETS,
            MySQLiteHelper.COLUMN_REPS
    };
    private String[] bodyMeasuresColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_RIGHT_UPPER_ARM,
            MySQLiteHelper.COLUMN_LEFT_UPPER_ARM,
            MySQLiteHelper.COLUMN_RIGHT_FOREARM,
            MySQLiteHelper.COLUMN_LEFT_FOREARM,
            MySQLiteHelper.COLUMN_CHEST,
            MySQLiteHelper.COLUMN_RIGHT_THIGH,
            MySQLiteHelper.COLUMN_LEFT_THIGH,
            MySQLiteHelper.COLUMN_RIGHT_CALF,
            MySQLiteHelper.COLUMN_LEFT_CALF,
            MySQLiteHelper.COLUMN_WAIST,
            MySQLiteHelper.COLUMN_SHOULDER
    };
    private String[] daysColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME
    };
    private String[] routineColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_DAY_ID,
            MySQLiteHelper.COLUMN_EXERCISE_REPETITIONS_ID
    };

    public MyDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /** ----------  CREATE  ---------- */


}
