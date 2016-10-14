package com.example.rodri.letsworkout.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by rodri on 8/20/2016.
 */
public class Util {

    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static String convertToDate(long timeInMillis, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);

        return formatter.format(cal.getTime());
    }

}
