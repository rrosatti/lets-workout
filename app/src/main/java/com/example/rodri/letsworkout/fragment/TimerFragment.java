package com.example.rodri.letsworkout.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private ImageButton imgChronometer;

    private boolean isStarted = false;
    private long startTime = 0;
    private Handler handler = new Handler();
    private long timeInMillis = 0;
    private long timeSwap = 0;
    private long finalTime = 0;

    private CountDownTimer countDownTimer;
    private long minutes = 0;
    private long seconds = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);

        initViews(v);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState != null) {
            isStarted = savedInstanceState.getBoolean(STATE_STARTED);
            if (isStarted) {
                timeSwap = savedInstanceState.getLong(STATE_TIME_SWAP);
                timeInMillis = savedInstanceState.getLong(STATE_TIME_IN_MILLIS);
                btStart.setText(getResources().getText(R.string.button_stop));

                //timeSwap += timeInMillis;
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
                countDownTimer.cancel();
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

        imgChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_chronometer);
                dialog.setTitle(R.string.dialog_set_chronometer);

                Button btConfirm = (Button) dialog.findViewById(R.id.dialogChronometer_btConfirm);
                final EditText etMinutes = (EditText) dialog.findViewById(R.id.dialogChronometer_etMinutes);
                final EditText etSeconds = (EditText) dialog.findViewById(R.id.dialogChronometer_etSeconds);

                btConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        minutes = Long.parseLong(etMinutes.getText().toString());
                        seconds = Long.parseLong(etSeconds.getText().toString());
                        Toast.makeText(getContext(), minutes+":"+seconds+"", Toast.LENGTH_SHORT).show();

                        long time = (seconds*1000) + (minutes*60*1000);

                        if (isStarted) {
                            isStarted = false;
                            //btStart.setText(getResources().getText(R.string.button_start));

                            //timeSwap += timeInMillis;
                            timeSwap = 0;
                            handler.removeCallbacks(updateTimerMethod);
                        }
                        btStart.setText(getResources().getText(R.string.button_stop));
                        isStarted = true;

                        seconds = 60;
                        countDownTimer = new CountDownTimer(time, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long auxSeconds = millisUntilFinished/1000;
                                if ( auxSeconds > 59) {
                                    minutes = (auxSeconds/60);
                                    //seconds--;
                                    seconds = auxSeconds%60;
                                    System.out.println("seconds: " + auxSeconds%60);
                                    if (seconds == 0) {
                                        minutes--;
                                    }
                                } else {
                                    seconds = millisUntilFinished/1000;
                                }

                                txtTimer.setText( minutes + " : " +
                                        seconds);
                            }

                            @Override
                            public void onFinish() {

                            }
                        }.start();

                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });

        return v;
    }

    public void initViews(View v) {
        txtTimer = (TextView) v.findViewById(R.id.fragmentTimer_txtTimer);
        btStart = (Button) v.findViewById(R.id.fragmentTimer_btStart);
        btReset = (Button) v.findViewById(R.id.fragmentTimer_btReset);
        imgChronometer = (ImageButton) v.findViewById(R.id.fragmentTimer_imgChronometer);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
