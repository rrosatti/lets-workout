package com.example.rodri.letsworkout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.letsworkout.model.BodyMeasure;
import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;
import com.example.rodri.letsworkout.model.MuscleGroup;
import com.example.rodri.letsworkout.model.Routine;

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
    private String[] usersColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME,
            MySQLiteHelper.COLUMN_LOGIN,
            MySQLiteHelper.COLUMN_PASSWORD
    };
    private String[] bodyColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_USER_ID,
            MySQLiteHelper.COLUMN_BODY_MEASURES_ID,
            MySQLiteHelper.COLUMN_WEIGHT,
            MySQLiteHelper.COLUMN_HEIGHT
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

    public BodyMeasure createBodyMeasure(double rightUpperArm, double leftUpperArm, double rightForearm, double leftForearm,
                                         double chest, double rightThigh, double leftThigh, double rightCalf, double leftCalf,
                                         double waist, double shoulder) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_RIGHT_UPPER_ARM, rightUpperArm);
        values.put(MySQLiteHelper.COLUMN_LEFT_UPPER_ARM, leftUpperArm);
        values.put(MySQLiteHelper.COLUMN_RIGHT_FOREARM, rightForearm);
        values.put(MySQLiteHelper.COLUMN_LEFT_FOREARM, leftForearm);
        values.put(MySQLiteHelper.COLUMN_CHEST, chest);
        values.put(MySQLiteHelper.COLUMN_RIGHT_THIGH, rightThigh);
        values.put(MySQLiteHelper.COLUMN_LEFT_THIGH, leftThigh);
        values.put(MySQLiteHelper.COLUMN_RIGHT_CALF, rightCalf);
        values.put(MySQLiteHelper.COLUMN_LEFT_CALF, leftCalf);
        values.put(MySQLiteHelper.COLUMN_WAIST, waist);
        values.put(MySQLiteHelper.COLUMN_SHOULDER, shoulder);

        long insertId = database.insert(MySQLiteHelper.TABLE_BODY_MEASURES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BODY_MEASURES, bodyMeasuresColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        BodyMeasure newBodyMeasure = cursorToBodyMeasure(cursor);
        cursor.close();

        return newBodyMeasure;
    }

    public Routine createRoutine(long dayId, long exerciseRepetitionId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DAY_ID, dayId);
        values.put(MySQLiteHelper.COLUMN_EXERCISE_REPETITIONS_ID, exerciseRepetitionId);

        long insertId = database.insert(MySQLiteHelper.TABLE_ROUTINE, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ROUTINE, routineColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Routine newRoutine = cursorToRoutine(cursor);
        cursor.close();

        return newRoutine;
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

    public BodyMeasure cursorToBodyMeasure(Cursor cursor) {
        BodyMeasure bodyMeasure = new BodyMeasure();
        bodyMeasure.setId(cursor.getLong(0));
        bodyMeasure.setRightUpperArm(cursor.getDouble(1));
        bodyMeasure.setLeftUpperArm(cursor.getDouble(2));
        bodyMeasure.setRightForearm(cursor.getDouble(3));
        bodyMeasure.setLeftForearm(cursor.getDouble(4));
        bodyMeasure.setChest(cursor.getDouble(5));
        bodyMeasure.setRightThigh(cursor.getDouble(6));
        bodyMeasure.setLeftThigh(cursor.getDouble(7));
        bodyMeasure.setRightCalf(cursor.getDouble(8));
        bodyMeasure.setLeftCalf(cursor.getDouble(9));
        bodyMeasure.setWaist(cursor.getDouble(10));
        bodyMeasure.setShoulder(cursor.getDouble(11));
        return bodyMeasure;
    }

    public Routine cursorToRoutine(Cursor cursor) {
        Routine routine = new Routine();
        routine.setId(cursor.getLong(0));
        routine.setDayId(cursor.getLong(1));
        routine.setExerciseRepetitionId(cursor.getLong(2));
        return routine;
    }

    /** ----------  UPDATE  ---------- */
    public void updateExercise(long id, long muscleGroupId, String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MUSCLE_GROUP_ID, muscleGroupId);
        values.put(MySQLiteHelper.KEY_NAME, name);
        database.update(MySQLiteHelper.TABLE_EXERCISES, values, MySQLiteHelper.KEY_ID + " =" + id, null);
    }

    public void updateExerciseRepetition(long id, long exerciseId, int sets, int reps) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_EXERCISE_ID, exerciseId);
        values.put(MySQLiteHelper.COLUMN_SETS, sets);
        values.put(MySQLiteHelper.COLUMN_REPS, reps);
        database.update(MySQLiteHelper.TABLE_EXERCISE_REPETITIONS, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void updateBodyMeasure(long id, double rightUpperArm, double leftUpperArm, double rightForearm, double leftForearm,
                                  double chest, double rightThigh, double leftThigh, double rightCalf, double leftCalf,
                                  double waist, double shoulder) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_RIGHT_UPPER_ARM, rightUpperArm);
        values.put(MySQLiteHelper.COLUMN_LEFT_UPPER_ARM, leftUpperArm);
        values.put(MySQLiteHelper.COLUMN_RIGHT_FOREARM, rightForearm);
        values.put(MySQLiteHelper.COLUMN_LEFT_FOREARM, leftForearm);
        values.put(MySQLiteHelper.COLUMN_CHEST, chest);
        values.put(MySQLiteHelper.COLUMN_RIGHT_THIGH, rightThigh);
        values.put(MySQLiteHelper.COLUMN_LEFT_THIGH, leftThigh);
        values.put(MySQLiteHelper.COLUMN_RIGHT_CALF, rightCalf);
        values.put(MySQLiteHelper.COLUMN_LEFT_CALF, leftCalf);
        values.put(MySQLiteHelper.COLUMN_WAIST, waist);
        values.put(MySQLiteHelper.COLUMN_SHOULDER, shoulder);
        database.update(MySQLiteHelper.TABLE_BODY_MEASURES, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void updateRoutine(long id, long dayId, long exerciseRepetitionId){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DAY_ID, dayId);
        values.put(MySQLiteHelper.COLUMN_EXERCISE_REPETITIONS_ID, exerciseRepetitionId);
        database.update(MySQLiteHelper.TABLE_ROUTINE, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    /** ----------  DELETE  ---------- */
    public void deleteExercise(long id) {
        System.out.println("The exercise with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_EXERCISES, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void deleteExerciseRepetition(long id) {
        System.out.println("The exercise repetition with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_EXERCISE_REPETITIONS, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void deleteBodyMeasure(long id) {
        System.out.println("The body measure with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_BODY_MEASURES, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void deleteRoutine(long id) {
        System.out.println("The routine with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_ROUTINE, MySQLiteHelper.KEY_ID + " = " + id, null);
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
