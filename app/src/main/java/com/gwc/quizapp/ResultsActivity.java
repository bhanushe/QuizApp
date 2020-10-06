package com.gwc.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.gwc.quizapp.configurations.Constants;

public class ResultsActivity extends AppCompatActivity {

    private String sessionUser;
    private int correctAnswersCount;
    private int totalQuestionsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initializeMembers();
    }

    private void initializeMembers() {
        sessionUser = getIntent().getStringExtra(Constants.COM_GWC_APPS_QUIZAPP_SESSION_NAME);
        correctAnswersCount = getIntent().getIntExtra(Constants.COM_GWC_APPS_QUIZAPP_CORRECT_ANSWERS_COUNT, 0);
        totalQuestionsCount = getIntent().getIntExtra(Constants.COM_GWC_APPS_QUIZAPP_TOTAL_QUESTIONS_COUNT, 0);

        ((TextView)findViewById(R.id.tv_session_name)).setText("Congratulations " +sessionUser+", you finished your session !!!");
        ((TextView)findViewById(R.id.tv_score)).setText("Your score is " +correctAnswersCount+" out of "+totalQuestionsCount);
    }
}