package com.ramasta.memorygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {
    public static final String EXTRA_TIME_GAME = "com.ramasta.memorygame.EXTRA_TIME";

    private TextView timer;
    private Button gameButton;
    private Button first;
    private Button second;
    private Button third;
    private Button fourth;
    private Button fifth;
    private Button sixth;
    private Button seventh;
    private Button eighth;
    private Button ninth;

    private CountDownTimer countDownTimer;
    private long timeLeftMilliseconds = 60000;
    private boolean timerRunning;
    private int seconds;
    private String timeleft;
    private List<Drawable> images;
    private List<Integer> finished;
    private List<Drawable> imgPair;
    private List<Button> buttons;
    private int[] cellPair;
    private int countFinish;
    private int joker;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private List<Integer> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        finished = new ArrayList<>();
        buttons = new ArrayList<>();
        imgPair = new ArrayList<>();
        cellPair = new int[2];
        countFinish = 0;

        timer = findViewById(R.id.timer);
        gameButton = findViewById(R.id.game_button);

        scores = new ArrayList<>();
        prefs = getSharedPreferences("Scores", Context.MODE_PRIVATE);
        editor = prefs.edit();

        if (!prefs.contains("1st")) {
            // initialize keys
            editor.putInt("1st", 0);
            editor.putInt("2nd", 0);
            editor.putInt("3rd", 0);
            editor.putInt("4th", 0);
            editor.putInt("5th", 0);
        }

        editor.apply();

        first = findViewById(R.id.first_cell);
        second = findViewById(R.id.second_cell);
        third = findViewById(R.id.third_cell);
        fourth = findViewById(R.id.fourth_cell);
        fifth = findViewById(R.id.fifth_cell);
        sixth = findViewById(R.id.sixth_cell);
        seventh = findViewById(R.id.seventh_cell);
        eighth = findViewById(R.id.eighth_cell);
        ninth = findViewById(R.id.ninth_cell);

        images = getShuffle();
        disableButtons();

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameButton.getText() == "AGAIN") {
                    stopTimer();
                    resetButtons();
                } else {
                    startCancel();
                }
            }
        });

        findViewById(R.id.exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning){
                    countDownTimer.cancel();
                }
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(1) && !buttons.contains(first)) {
                    setBG(first, images.get(0));
                    setPair(1, images.get(0));
                    checkPair();
                    checkGame();
                }
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(2) && !buttons.contains(second)) {
                    setBG(second, images.get(1));
                    setPair(2, images.get(1));
                    checkPair();
                    checkGame();
                }
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(3) && !buttons.contains(third)) {
                    setBG(third, images.get(2));
                    setPair(3, images.get(2));
                    checkPair();
                    checkGame();
                }
            }
        });

        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(4) && !buttons.contains(fourth)) {
                    setBG(fourth, images.get(3));
                    setPair(4, images.get(3));
                    checkPair();
                    checkGame();
                }
            }
        });

        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(5) && !buttons.contains(fifth)) {
                    setBG(fifth, images.get(4));
                    setPair(5, images.get(4));
                    checkPair();
                    checkGame();
                }
            }
        });

        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(6) && !buttons.contains(sixth)) {
                    setBG(sixth, images.get(5));
                    setPair(6, images.get(5));
                    checkPair();
                    checkGame();
                }
            }
        });

        seventh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(7) && !buttons.contains(seventh)) {
                    setBG(seventh, images.get(6));
                    setPair(7, images.get(6));
                    checkPair();
                    checkGame();
                }
            }
        });

        eighth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(8) && !buttons.contains(eighth)) {
                    setBG(eighth, images.get(7));
                    setPair(8, images.get(7));
                    checkPair();
                    checkGame();
                }
            }
        });

        ninth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNotDone(9) && !buttons.contains(ninth)) {
                    setBG(ninth, images.get(8));
                    setPair(9, images.get(8));
                    checkPair();
                    checkGame();
                }
            }
        });
    }

    private List<Drawable> getShuffle() {

        Drawable jokeImage = ContextCompat.getDrawable(getApplicationContext(), R.drawable.joker);
        Drawable iics = ContextCompat.getDrawable(getApplicationContext(), R.drawable.iics);
        Drawable izone = ContextCompat.getDrawable(getApplicationContext(), R.drawable.izone);
        Drawable tiger = ContextCompat.getDrawable(getApplicationContext(), R.drawable.usttiger);
        Drawable twice = ContextCompat.getDrawable(getApplicationContext(), R.drawable.twice);
        List<Drawable> list = new ArrayList<>();

        list.add(iics);
        list.add(izone);
        list.add(tiger);
        list.add(twice);
        list.add(iics);
        list.add(izone);
        list.add(tiger);
        list.add(twice);
        list.add(jokeImage);

        Collections.shuffle(list);

        joker = list.indexOf(jokeImage);

        return list;
    }

    private Button findJokerButton() {
        Button btn = first;

        switch (joker) {
            case 0:
                btn = first;
                break;
            case 1:
                btn = second;
                break;
            case 2:
                btn = third;
                break;
            case 3:
                btn = fourth;
                break;
            case 4:
                btn = fifth;
                break;
            case 5:
                btn = sixth;
                break;
            case 6:
                btn = seventh;
                break;
            case 7:
                btn = eighth;
                break;
            case 8:
                btn = ninth;
                break;
        }
        return btn;
    }

    private boolean itemNotDone(int buttonNum) {
        for (int i = 0; i < finished.size(); i++) {
            if (finished.get(i) == buttonNum) {
                return false;
            }
        }
        return true;
    }

    private void setPair(int num, Drawable img) {
        if (cellPair[0] < 10) {
            cellPair[1] = num;
        } else {
            cellPair[0] = num;
        }
        imgPair.add(img);
    }

    private void checkPair() {
        if (cellPair[0] < 10 && cellPair[1] < 10) {
            if (imgPair.get(0) == imgPair.get(1)) {
                finished.add(cellPair[0]);
                finished.add(cellPair[1]);
                countFinish += 1;

                buttons.get(0).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bluesmiley));
                buttons.get(1).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bluesmiley));

                resetAll();
            } else {
                disableButtons();
                gameButton.setEnabled(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pauseWrongMatch();
                    }
                }, 3000);
            }
        }
    }

    private void setBG(Button button, Drawable img) {
        Log.d("Image: ", "" + img);
        button.setBackground(img);
        buttons.add(button);
    }

    private void pauseWrongMatch() {
        buttons.get(0).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bluecover));
        buttons.get(1).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bluecover));

        resetAll();
        gameButton.setEnabled(true);
        enableButtons();
    }

    private void checkGame() {
        if (countFinish == 4) {
            countDownTimer.cancel();
            gameButton.setText("AGAIN");
            timerRunning = false;

            findJokerButton().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bluesmiley));

            disableButtons();

            if (seconds > 50) {
                Toast.makeText(getApplicationContext(), "God-like Gamer Lezgo!", Toast.LENGTH_SHORT).show();
            } else if (seconds > 40) {
                Toast.makeText(getApplicationContext(), "That's kinda fast! Well Done!", Toast.LENGTH_SHORT).show();
            } else if (seconds > 30) {
                Toast.makeText(getApplicationContext(), "Mhm... Not bad!", Toast.LENGTH_SHORT).show();
            } else if (seconds > 10) {
                Toast.makeText(getApplicationContext(), "Congrats I guess...", Toast.LENGTH_SHORT).show();
            } else if (seconds > 0) {
                Toast.makeText(getApplicationContext(), "WOW! CONGRATULATIONS! GREAT JOB!", Toast.LENGTH_SHORT).show();
            }

            scores.add(prefs.getInt("1st", 0));
            scores.add(prefs.getInt("2nd", 0));
            scores.add(prefs.getInt("3rd", 0));
            scores.add(prefs.getInt("4th", 0));
            scores.add(prefs.getInt("5th", 0));
            scores.add(seconds);

            // sorts ascending
            Collections.sort(scores);

            // update top scores which are last items of list
            editor.putInt("1st", scores.get(5));
            editor.putInt("2nd", scores.get(4));
            editor.putInt("3rd", scores.get(3));
            editor.putInt("4th", scores.get(2));
            editor.putInt("5th", scores.get(1));

            editor.apply();
            scores.clear();
        }
    }

    private void disableButtons() {
        first.setEnabled(false);
        second.setEnabled(false);
        third.setEnabled(false);
        fourth.setEnabled(false);
        fifth.setEnabled(false);
        sixth.setEnabled(false);
        seventh.setEnabled(false);
        eighth.setEnabled(false);
        ninth.setEnabled(false);
    }

    private void enableButtons() {
        first.setEnabled(true);
        second.setEnabled(true);
        third.setEnabled(true);
        fourth.setEnabled(true);
        fifth.setEnabled(true);
        sixth.setEnabled(true);
        seventh.setEnabled(true);
        eighth.setEnabled(true);
        ninth.setEnabled(true);
    }

    private void resetAll() {
        buttons.clear();
        imgPair.clear();
        cellPair[0] = 10;
        cellPair[1] = 10;
    }

    private void resetButtons() {
        Drawable blueCover = ContextCompat.getDrawable(getApplicationContext(), R.drawable.bluecover);

        first.setBackground(blueCover);
        second.setBackground(blueCover);
        third.setBackground(blueCover);
        fourth.setBackground(blueCover);
        fifth.setBackground(blueCover);
        sixth.setBackground(blueCover);
        seventh.setBackground(blueCover);
        eighth.setBackground(blueCover);
        ninth.setBackground(blueCover);
    }

    private void startCancel() {
        if (timerRunning) {
            stopTimer();
            disableButtons();
            resetAll();
            resetButtons();
            finished.clear();
        } else {
            images = getShuffle();
            countFinish = 0;
            finished.clear();
            resetAll();
            enableButtons();
            startTimer();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                if (timeleft.equals("0:01")) {
                    timeleft = "0:00";
                    timer.setText(timeleft);
                }
                disableButtons();
                Toast.makeText(getApplicationContext(), "Never Gonna Keep You Up", Toast.LENGTH_SHORT).show();
                gameButton.setText("AGAIN");
            }
        }.start();

        gameButton.setText("QUIT");
        timerRunning = true;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        gameButton.setText("START");
        timerRunning = false;
        timeLeftMilliseconds = 60000;
        timer.setText("1:00");
        countFinish = 0;
    }

    private void updateTimer() {
        seconds = (int) timeLeftMilliseconds % 60000 / 1000;

        timeleft = "0:";
        if (seconds < 10) {
            timeleft += "0";
        }
        timeleft += String.valueOf(seconds);

        timer.setText(timeleft);
    }

}