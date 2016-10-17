package com.example.rodri.letsworkout.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;

/**
 * Created by rodri on 9/8/2016.
 */
public class TimerFragment extends Fragment {

    private static final String STATE_STARTED = "isStarted";
    private static final String STATE_TIME_SWAP = "timeSwap";
    private static final String STATE_TIME_IN_MILLIS = "timeInMillis";
    private static final String STATE_START_TIME = "startTime";
    private static final String STATE_FINAL_TIME = "finalTime";
    private static final String STATE_TXT_TIMER = "txtTimer";

    private TextView txtTimer;
    private Button btStart;
    private Button btReset;
    private boolean isStarted = false;

    private long startTime = 0;
    private Handler handler = new Handler();
    private long timeInMillis = 0;
    private long timeSwap = 0;
    private long finalTime = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);

        initViews(v);

        if (savedInstanceState != null) {
            isStarted = savedInstanceState.getBoolean(STATE_STARTED);
            if (isStarted) {
                timeSwap = savedInstanceState.getLong(STATE_TIME_SWAP);
                timeInMillis = savedInstanceState.getLong(STATE_TIME_IN_MILLIS);
                btStart.setText(getResources().getText(R.string.button_stop));

                timeSwap += timeInMillis;
                startTime = savedInstanceState.getLong(STATE_START_TIME);
                finalTime = savedInstanceState.getLong(STATE_FINAL_TIME);
                handler.postDelayed(updateTimerMethod, 0);
            } else {
                timeSwap = savedInstanceState.getLong(STATE_TIME_SWAP);
                timeInMillis = savedInstanceState.getLong(STATE_TIME_IN_MILLIS);
                handler.removeCallbacks(updateTimerMethod);
                String time = savedInstanceState.getString(STATE_TXT_TIMER);
                txtTimer.setText(time);
            }


        }

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarted) {
                    isStarted = false;
                    btStart.setText(getResources().getText(R.string.button_start));

                    timeSwap += timeInMillis;
                    handler.removeCallbacks(updateTimerMethod);
                } else {
                    isStarted = true;
                    btStart.setText(getResources().getText(R.string.button_stop));

                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerMethod, 0);
                }

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarted) {
                    isStarted = false;
                    btStart.setText(getResources().getText(R.string.button_start));

                    timeSwap = 0;
                    handler.removeCallbacks(updateTimerMethod);
                    txtTimer.setText(getResources().getText(R.string.text_view_timer));
                } else {
                    timeSwap = 0;
                    handler.removeCallbacks(updateTimerMethod);
                    txtTimer.setText(getResources().getText(R.string.text_view_timer));
                }
            }
        });

        return v;
    }

    public void initViews(View v) {
        txtTimer = (TextView) v.findViewById(R.id.fragmentTimer_txtTimer);
        btStart = (Button) v.findViewById(R.id.fragmentTimer_btStart);
        btReset = (Button) v.findViewById(R.id.fragmentTimer_btReset);
    }

    private Runnable updateTimerMethod = new Runnable() {
        @Override
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillis;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            txtTimer.setText("" + minutes + " : " + String.format("%02d", seconds) + " : " + String.format("%02d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(STATE_STARTED, isStarted);
        outState.putLong(STATE_TIME_SWAP, timeSwap);
        outState.putLong(STATE_TIME_IN_MILLIS, timeInMillis);
        outState.putLong(STATE_START_TIME, startTime);
        outState.putLong(STATE_FINAL_TIME, finalTime);
        outState.putString(STATE_TXT_TIMER, txtTimer.getText().toString());
    }
}
