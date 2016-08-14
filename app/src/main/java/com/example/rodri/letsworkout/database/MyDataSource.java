package com.example.rodri.letsworkout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;
import com.example.rodri.letsworkout.model.MuscleGroup;

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

    public Exercise createExercise(long muscleGroupId, String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MUSCLE_GROUP_ID, muscleGroupId);
        values.put(MySQLiteHelper.KEY_NAME, name);

        long insertId = database.insert(MySQLiteHelper.TABLE_EXERCISES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISES, exercisesColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Exercise newExercise = cursorToExercise(cursor);
        cursor.close();

        return newExercise;
    }

    public ExerciseRepetition createExerciseRepetition(long exerciseId, int sets, int reps) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_EXERCISE_ID, exerciseId);
        values.put(MySQLiteHelper.COLUMN_SETS, sets);
        values.put(MySQLiteHelper.COLUMN_REPS, reps);

        long insertId = database.insert(MySQLiteHelper.TABLE_EXERCISE_REPETITIONS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISE_REPETITIONS, exerciseRepetitionsColumns,
                MySQLiteHelper.KEY_ID  + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        ExerciseRepetition newExerciseRepetition = cursorToExerciseRepetition(cursor);
        cursor.close();

        return newExerciseRepetition;

    }

    /** ---------  CURSOR TO  ---------- */

    public Exercise cursorToExercise(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setMuscleGroupId(cursor.getLong(1));
        exercise.setName(cursor.getString(2));
        return exercise;
    }

    public ExerciseRepetition cursorToExerciseRepetition(Cursor cursor) {
        ExerciseRepetition exerciseRepetition = new ExerciseRepetition();
        exerciseRepetition.setId(cursor.getLong(0));
        exerciseRepetition.setExerciseId(cursor.getLong(1));
        exerciseRepetition.setSets(cursor.getInt(2));
        exerciseRepetition.setReps(cursor.getInt(3));
        return exerciseRepetition;
    }

    /** ----------  OTHER  ---------- */

    public boolean isCursorEmpty(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

}
