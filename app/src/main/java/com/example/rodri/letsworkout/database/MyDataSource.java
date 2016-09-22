package com.example.rodri.letsworkout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.letsworkout.model.Day;
import com.example.rodri.letsworkout.model.MuscleGroup;
import com.example.rodri.letsworkout.model.RoutineExercises;
import com.example.rodri.letsworkout.model.UserBody;
import com.example.rodri.letsworkout.model.BodyMeasure;
import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;
import com.example.rodri.letsworkout.model.Routine;
import com.example.rodri.letsworkout.model.User;

import java.util.ArrayList;
import java.util.List;

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
            MySQLiteHelper.COLUMN_SHOULDER,
            MySQLiteHelper.COLUMN_WEIGHT,
            MySQLiteHelper.COLUMN_HEIGHT,
            MySQLiteHelper.COLUMN_DATE
    };
    private String[] daysColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME
    };
    private String[] routineColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_DAY_ID,
            MySQLiteHelper.COLUMN_USER_ID
    };
    private String[] usersColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME,
            MySQLiteHelper.COLUMN_LOGIN,
            MySQLiteHelper.COLUMN_PASSWORD
    };
    private String[] userBodyColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_USER_ID,
            MySQLiteHelper.COLUMN_BODY_MEASURES_ID,
    };
    private String[] routineExercisesColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_ROUTINE_ID,
            MySQLiteHelper.COLUMN_EXERCISE_REPETITION_ID
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

        long insertId = database.insert(MySQLiteHelper.TABLE_EXERCISE, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISE, exercisesColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
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

        long insertId = database.insert(MySQLiteHelper.TABLE_EXERCISE_REPETITION, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISE_REPETITION, exerciseRepetitionsColumns,
                MySQLiteHelper.KEY_ID  + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
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
                                         double waist, double shoulder, double weight, double height, long date) {
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
        values.put(MySQLiteHelper.COLUMN_WEIGHT, weight);
        values.put(MySQLiteHelper.COLUMN_HEIGHT, height);
        values.put(MySQLiteHelper.COLUMN_DATE, date);

        long insertId = database.insert(MySQLiteHelper.TABLE_BODY_MEASURE, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BODY_MEASURE, bodyMeasuresColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        BodyMeasure newBodyMeasure = cursorToBodyMeasure(cursor);
        cursor.close();

        return newBodyMeasure;
    }

    public Routine createRoutine(long dayId, long userId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DAY_ID, dayId);
        values.put(MySQLiteHelper.COLUMN_USER_ID, userId);

        long insertId = database.insert(MySQLiteHelper.TABLE_ROUTINE, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ROUTINE, routineColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Routine newRoutine = cursorToRoutine(cursor);
        cursor.close();

        return newRoutine;
    }

    public User createUser(String name, String login, String password) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME, name);
        values.put(MySQLiteHelper.COLUMN_LOGIN, login);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);

        long insertId = database.insert(MySQLiteHelper.TABLE_USER, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, usersColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        User newUser = cursorToUser(cursor);
        cursor.close();

        return newUser;
    }

    public UserBody createUserBody(long userId, long bodyMeasureId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USER_ID, userId);
        values.put(MySQLiteHelper.COLUMN_BODY_MEASURES_ID, bodyMeasureId);

        long insertId = database.insert(MySQLiteHelper.TABLE_USER_BODY, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER_BODY, userBodyColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        UserBody newUserBody = cursorToUserBody(cursor);
        BodyMeasure bodyMeasure = getBodyMeasure(bodyMeasureId);
        newUserBody.setBodyMeasure(bodyMeasure);

        cursor.close();

        return newUserBody;
    }

    public RoutineExercises createRoutineExercises(long routineId, long exerciseRepetitionId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ROUTINE_ID, routineId);
        values.put(MySQLiteHelper.COLUMN_EXERCISE_REPETITION_ID, exerciseRepetitionId);

        long insertId = database.insert(MySQLiteHelper.TABLE_ROUTINE_EXERCISES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ROUTINE_EXERCISES, routineExercisesColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            RoutineExercises newRoutineExercises = cursorToRoutineExercises(cursor);
            cursor.close();
            return newRoutineExercises;
        } else {
            cursor.close();
            return null;
        }
    }

    /** ---------  CURSOR TO  ---------- */

    public Exercise cursorToExercise(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));
        exercise.setMuscleGroupId(cursor.getLong(2));
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
        bodyMeasure.setWeight(cursor.getDouble(12));
        bodyMeasure.setHeight(cursor.getDouble(13));
        bodyMeasure.setDate(cursor.getLong(14));
        return bodyMeasure;
    }

    public Routine cursorToRoutine(Cursor cursor) {
        Routine routine = new Routine();
        routine.setId(cursor.getLong(0));
        routine.setDayId(cursor.getLong(1));
        routine.setUserId(cursor.getLong(2));
        return routine;
    }

    public User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setLogin(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        return user;
    }

    public UserBody cursorToUserBody(Cursor cursor) {
        UserBody userBody = new UserBody();
        userBody.setId(cursor.getLong(0));
        userBody.setUserId(cursor.getLong(1));
        userBody.setBodyMeasuresId(cursor.getLong(2));
        return userBody;
    }

    public MuscleGroup cursorToMuscleGroup(Cursor cursor) {
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setId(cursor.getLong(0));
        muscleGroup.setName(cursor.getString(1));
        return muscleGroup;
    }

    public RoutineExercises cursorToRoutineExercises(Cursor cursor) {
        RoutineExercises routineExercises = new RoutineExercises();
        routineExercises.setId(cursor.getLong(0));
        routineExercises.setRoutineId(cursor.getLong(1));
        routineExercises.setExerciseRepetitionId(cursor.getLong(2));
        return routineExercises;
    }

    public Day cursorToDay(Cursor cursor) {
        Day day = new Day();
        day.setId(cursor.getLong(0));
        day.setName(cursor.getString(1));
        return day;
    }

    /** ----------  GET DATA  ---------- */

    // Exercise, ExerciseRepetition, BodyMeasure, Routine, User, UserBody

    public Exercise getExercise(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISE, exercisesColumns,
                MySQLiteHelper.KEY_ID + " = " + id, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            Exercise exercise = cursorToExercise(cursor);
            cursor.close();
            return exercise;

        } else {
            cursor.close();
            return null;
        }

    }

    public Exercise getExercise(String name) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISE, exercisesColumns,
                MySQLiteHelper.KEY_NAME + " LIKE('%" + name + "%')", null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            Exercise exercise = cursorToExercise(cursor);
            cursor.close();
            return exercise;
        } else {
            cursor.close();
            return null;
        }
    }

    public ExerciseRepetition getExerciseRepetition(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISE_REPETITION, exerciseRepetitionsColumns,
                MySQLiteHelper.KEY_ID + " = " + id, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            ExerciseRepetition exerciseRepetition = cursorToExerciseRepetition(cursor);
            cursor.close();
            return exerciseRepetition;

        } else {
            cursor.close();
            return null;
        }

    }

    public BodyMeasure getBodyMeasure(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BODY_MEASURE, bodyMeasuresColumns,
                MySQLiteHelper.KEY_ID + " = " + id, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            BodyMeasure bodyMeasure = cursorToBodyMeasure(cursor);
            cursor.close();
            return bodyMeasure;

        } else {
            cursor.close();
            return null;
        }

    }

    public Routine getRoutine(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ROUTINE, routineColumns,
                MySQLiteHelper.KEY_ID + " = " + id, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            Routine routine = cursorToRoutine(cursor);
            cursor.close();
            return routine;

        } else {
            cursor.close();
            return null;
        }

    }

    public User getUser(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, usersColumns,
                MySQLiteHelper.KEY_ID + " = " + id, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            User user = cursorToUser(cursor);
            cursor.close();
            return user;

        } else {
            cursor.close();
            return null;
        }

    }

    public User getUser(String login, String password) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, usersColumns,
                MySQLiteHelper.COLUMN_LOGIN + " = '" + login + "' AND " + MySQLiteHelper.COLUMN_PASSWORD + " = '" + password + "'",
                null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            User user = cursorToUser(cursor);
            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }

    }

    public UserBody getBody(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER_BODY, userBodyColumns,
                MySQLiteHelper.KEY_ID + " = " + id, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            UserBody userBody = cursorToUserBody(cursor);
            cursor.close();
            return userBody;
        } else {
            cursor.close();
            return null;
        }


    }

    public List<Exercise> getExercises(long muscleGroupId) {
        List<Exercise> exercises = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISE, exercisesColumns,
                MySQLiteHelper.COLUMN_MUSCLE_GROUP_ID + " = " + muscleGroupId, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                exercises.add(cursorToExercise(cursor));
                cursor.moveToNext();
            }
            cursor.close();

            return exercises;

        } else {
            cursor.close();
            return null;
        }

    }

    public List<MuscleGroup> getMuscleGroups() {
        List<MuscleGroup> muscleGroups = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MUSCLE_GROUP, muscleGroupsColumns,
                null, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                muscleGroups.add(cursorToMuscleGroup(cursor));
                cursor.moveToNext();
            }
            cursor.close();

            return muscleGroups;

        } else {
            cursor.close();
            return null;
        }
    }

    public List<RoutineExercises> getRoutineExercises(long routineId) {
        List<RoutineExercises> routineExercises = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ROUTINE_EXERCISES, routineExercisesColumns,
                MySQLiteHelper.COLUMN_ROUTINE_ID + " = " + routineId, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                routineExercises.add(cursorToRoutineExercises(cursor));
                cursor.moveToNext();
            }
            cursor.close();
            return routineExercises;
        } else {
            cursor.close();
            return null;
        }

    }

    public List<Day> getDays() {
        List<Day> days = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYS, daysColumns,
                null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                days.add(cursorToDay(cursor));
                cursor.moveToNext();
            }
            cursor.close();
            return days;
        } else {
            cursor.close();
            return null;
        }
    }

    /** ----------  UPDATE  ---------- */
    public void updateExercise(long id, long muscleGroupId, String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MUSCLE_GROUP_ID, muscleGroupId);
        values.put(MySQLiteHelper.KEY_NAME, name);
        database.update(MySQLiteHelper.TABLE_EXERCISE, values, MySQLiteHelper.KEY_ID + " =" + id, null);
    }

    public void updateExerciseRepetition(long id, long exerciseId, int sets, int reps) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_EXERCISE_ID, exerciseId);
        values.put(MySQLiteHelper.COLUMN_SETS, sets);
        values.put(MySQLiteHelper.COLUMN_REPS, reps);
        database.update(MySQLiteHelper.TABLE_EXERCISE_REPETITION, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void updateBodyMeasure(long id, double rightUpperArm, double leftUpperArm, double rightForearm, double leftForearm,
                                  double chest, double rightThigh, double leftThigh, double rightCalf, double leftCalf,
                                  double waist, double shoulder, double weight, double height) {
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
        values.put(MySQLiteHelper.COLUMN_WEIGHT, weight);
        values.put(MySQLiteHelper.COLUMN_HEIGHT, height);
        database.update(MySQLiteHelper.TABLE_BODY_MEASURE, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void updateRoutine(long id, long dayId){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DAY_ID, dayId);
        database.update(MySQLiteHelper.TABLE_ROUTINE, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    /** ----------  DELETE  ---------- */
    public void deleteExercise(long id) {
        System.out.println("The exercise with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_EXERCISE, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void deleteExerciseRepetition(long id) {
        System.out.println("The exercise repetition with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_EXERCISE_REPETITION, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void deleteBodyMeasure(long id) {
        System.out.println("The body measure with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_BODY_MEASURE, MySQLiteHelper.KEY_ID + " = " + id, null);
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
            System.out.println("Error! Cursor is empty!");
            return true;
        }
    }

    public boolean isThereAnyBodyRegistered(long userId) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER_BODY, userBodyColumns,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null, null, null, null, null);

        if (!isCursorEmpty(cursor)) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public long getLatestBodyMeasureId(long userId) {
        /**Cursor cursor = database.rawQuery("SELECT" + MySQLiteHelper.COLUMN_BODY_MEASURES_ID + "FROM "
                + MySQLiteHelper.TABLE_USER_BODY  + " WHERE " + MySQLiteHelper.KEY_ID + " = " + userId
                + " ORDER BY " + MySQLiteHelper.COLUMN_BODY_MEASURES_ID + " DESC;", null);*/
        Cursor cursor = database.rawQuery("SELECT max(" + MySQLiteHelper.COLUMN_BODY_MEASURES_ID + ") FROM "
                + MySQLiteHelper.TABLE_USER_BODY + " WHERE " + MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null);

        if (!isCursorEmpty(cursor)) {
            long bodyMeasureId = cursor.getLong(0);
            cursor.close();
            return bodyMeasureId;
        } else {
            cursor.close();
            return -1;
        }

    }

}
