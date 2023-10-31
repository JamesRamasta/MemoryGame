package com.ramasta.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Scores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        TextView firstPlace = findViewById(R.id.first_place);
        TextView secondPlace = findViewById(R.id.second_place);
        TextView thirdPlace = findViewById(R.id.third_place);
        TextView fourthPlace = findViewById(R.id.fourth_place);
        TextView fifthPlace = findViewById(R.id.fifth_place);

        SharedPreferences preferences = getSharedPreferences("Scores", MODE_PRIVATE);

        String one = "1st";
        String two = "2nd";
        String three = "3rd";
        String four = "fourth";
        String five = "five";

        setScoresText(firstPlace, preferences.getInt(one, 0));
        setScoresText(secondPlace, preferences.getInt(two, 0));
        setScoresText(thirdPlace, preferences.getInt(three, 0));
        setScoresText(fourthPlace, preferences.getInt(four, 0));
        setScoresText(fifthPlace, preferences.getInt(five, 0));

        findViewById(R.id.exit_score_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setScoresText(TextView btn, int score){
        if (score == 0) {
            btn.setText("-:--");
        } else if (score < 10) {
            btn.setText("0:0" + score);
        } else {
            btn.setText("0:" + score);
        }
    }
}