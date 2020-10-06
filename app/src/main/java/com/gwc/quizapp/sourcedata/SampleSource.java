package com.gwc.quizapp.sourcedata;

import android.util.Log;

import com.gwc.quizapp.model.Answer;
import com.gwc.quizapp.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleSource implements Source {

    private Random random = new Random();

    private static final String TAG = "SampleSource";

    @Override
    public List<Question> getListOfQuestions(int count) {
        Log.d(TAG, "getListOfQuestions: ");

        List<Question> questions = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            questions.add(createSampleQuestion(i));
        }

        Log.d(TAG, "getListOfQuestions: returning "+questions.size()+" questions ");
        return questions;
    }

    private Question createSampleQuestion(int i) {
        List<Answer> answers = new ArrayList<>(4);
        answers.add(new Answer(1, "Math"));
        answers.add(new Answer(2, "Science"));
        answers.add(new Answer(3, "History"));
        answers.add(new Answer(4, "None"));

        int correctAnswerId = random.nextInt(3) + 1;
        return new Question(1, "What is your favorite subject?", answers, correctAnswerId);
    }

}