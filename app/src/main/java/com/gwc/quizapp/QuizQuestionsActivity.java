package com.gwc.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gwc.quizapp.configurations.Constants;
import com.gwc.quizapp.model.Answer;
import com.gwc.quizapp.model.Question;
import com.gwc.quizapp.provider.QuestionProvider;
import com.gwc.quizapp.sourcedata.SourceType;

import java.util.ArrayList;
import java.util.List;

import static com.gwc.quizapp.configurations.Constants.COM_GWC_APPS_QUIZAPP_SESSION_NAME;

public class QuizQuestionsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "QuizQuestionsActivity";
    private QuestionProvider questionProvider;
    private int currentQuestionPosition = 0;
    private Question currentQuestion;
    private ArrayList<TextView> optionsTVList;
    private Button btnSubmit;
    private TextView correctAnswerOption;
    private TextView selectedAnswerOption;
    private boolean showNextQuestion;
    private boolean validateAnswer;
    private int currentProgressPercent=0;
    private int countOfCorrectAnswerInCurrentSession=0;
    private String sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_questions);

        initializeMembers();

        if(questionProvider.getCountOfQuestionsInQueue() > 0) {
            setQuestion();
        }
    }

    private void initializeMembers() {
        optionsTVList = new ArrayList<>(4);
        TextView option1 = findViewById(R.id.tv_option_one);
        optionsTVList.add(option1);
        TextView option2 = findViewById(R.id.tv_option_two);
        optionsTVList.add(option2);
        TextView option3 = findViewById(R.id.tv_option_three);
        optionsTVList.add(option3);
        TextView option4 = findViewById(R.id.tv_option_four);
        optionsTVList.add(option4);
        questionProvider = QuestionProvider.get_instance(SourceType.SAMPLE_SOURCE);

        btnSubmit = findViewById(R.id.btn_submit);

        option1.setOnClickListener(QuizQuestionsActivity.this);
        option2.setOnClickListener(QuizQuestionsActivity.this);
        option3.setOnClickListener(QuizQuestionsActivity.this);
        option4.setOnClickListener(QuizQuestionsActivity.this);
        btnSubmit.setOnClickListener(QuizQuestionsActivity.this);

        sessionUser = getIntent().getStringExtra(Constants.COM_GWC_APPS_QUIZAPP_SESSION_NAME);
    }

    private void setQuestion() {
        resetDefaults();
        currentQuestionPosition++;
        currentQuestion = questionProvider.getNextQuestion();
        Log.d(TAG, "onCreate: Display Question "+ currentQuestion);

        setOptionsView(null);

        currentProgressPercent = currentQuestionPosition * 100 / (currentQuestionPosition + questionProvider.getCountOfQuestionsInQueue());
        Log.d(TAG, "setQuestion: current progress percent "+currentProgressPercent);
        ((TextView)findViewById(R.id.tv_question)).setText(currentQuestion.getQuestion());
        ((ProgressBar)findViewById(R.id.progressBar)).setProgress(currentProgressPercent);

        String currentProgress = currentQuestionPosition + "/" + (currentQuestionPosition + questionProvider.getCountOfQuestionsInQueue());
        ((TextView)findViewById(R.id.tv_progress)).setText(currentProgress);

        List<Answer> options = currentQuestion.getOptions();
        TextView optionOne = (TextView)findViewById(R.id.tv_option_one);
        TextView optionTwo = (TextView)findViewById(R.id.tv_option_two);
        TextView optionThree = (TextView)findViewById(R.id.tv_option_three);
        TextView optionFour = (TextView)findViewById(R.id.tv_option_four);

        setSpecifiedOptionForCurrentQuestion(options.get(0), optionOne);
        setSpecifiedOptionForCurrentQuestion(options.get(1), optionTwo);
        setSpecifiedOptionForCurrentQuestion(options.get(2), optionThree);
        setSpecifiedOptionForCurrentQuestion(options.get(3), optionFour);
    }

    private void resetDefaults() {
        selectedAnswerOption = null;
        correctAnswerOption = null;
        showNextQuestion = false;
        validateAnswer = false;
        btnSubmit.setText("Submit");
    }

    private void setSpecifiedOptionForCurrentQuestion(Answer answerOption, TextView textviewOption) {
        textviewOption.setText(answerOption.getAnswer());
        if(answerOption.getId() == currentQuestion.getCorrectAnswerId()){
            correctAnswerOption = textviewOption;
        }
    }

    private void setOptionsView(TextView selectedOption) {
        if(null == optionsTVList) {
            initializeMembers();
        }

        for (TextView option : optionsTVList){
            if(option.equals(selectedOption)) {
                validateAnswer = true;
                showNextQuestion = false;
                selectedAnswerOption = option;
                setOptionsViewBySelection(option, "#363A43", Typeface.DEFAULT_BOLD, R.drawable.selected_option_border_bg);
            } else {
                setOptionsViewBySelection(option, "#7A8089", Typeface.DEFAULT, R.drawable.default_option_border_bg);
            }
        }
    }

    private void setOptionsViewBySelection(TextView option, String s, Typeface defaultBold, int p) {
        option.setTextColor(Color.parseColor(s));
        option.setTypeface(defaultBold);
        option.setBackground(ContextCompat.getDrawable(QuizQuestionsActivity.this, p));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnSubmit.getId() ) {
            if (null == selectedAnswerOption) {
                Toast.makeText(QuizQuestionsActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }
            if(showNextQuestion){
                setQuestion();
            } else if (validateAnswer) {
                validateAnswer();
            } else {
                Toast toast = Toast.makeText(QuizQuestionsActivity.this, "You have successfully completed the quiz !!!", Toast.LENGTH_LONG);
                toast.show();
                launchActivity(ResultsActivity.class);
            }
        }  else {
            setOptionsView((TextView) v);
        }

    }

    private void launchActivity(Class<ResultsActivity> resultsActivityClass) {
        Intent intent = new Intent(QuizQuestionsActivity.this, resultsActivityClass);
        Log.d(TAG, "launchActivity: session user name "+sessionUser);
        intent.putExtra(COM_GWC_APPS_QUIZAPP_SESSION_NAME, sessionUser);

        Log.d(TAG, "launchActivity: count of correct answers "+countOfCorrectAnswerInCurrentSession);
        intent.putExtra(Constants.COM_GWC_APPS_QUIZAPP_CORRECT_ANSWERS_COUNT, countOfCorrectAnswerInCurrentSession);

        Log.d(TAG, "launchActivity: Total questions answered in session "+currentQuestionPosition);
        intent.putExtra(Constants.COM_GWC_APPS_QUIZAPP_TOTAL_QUESTIONS_COUNT, currentQuestionPosition);
        startActivity(intent);
        finish();
    }

    private void validateAnswer() {
        validateAnswer = false;
        showNextQuestion = true;
        if(currentProgressPercent == 100 ){
            showNextQuestion = false;
            btnSubmit.setText("Finish Quiz");
        }else{
            btnSubmit.setText("Next Question");
        }
        if (selectedAnswerOption == correctAnswerOption){
            countOfCorrectAnswerInCurrentSession++;
            setOptionsViewBySelection(selectedAnswerOption, "#363A43", Typeface.DEFAULT_BOLD, R.drawable.correct_option_border_bg);
        }else {
            setOptionsViewBySelection(correctAnswerOption, "#363A43", Typeface.DEFAULT_BOLD, R.drawable.correct_option_border_bg);
            setOptionsViewBySelection(selectedAnswerOption, "#363A43", Typeface.DEFAULT_BOLD, R.drawable.wrong_option_border_bg);
        }
    }
}