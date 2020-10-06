package com.gwc.quizapp.provider;

import android.util.Log;

import com.gwc.quizapp.model.Question;
import com.gwc.quizapp.sourcedata.Source;
import com.gwc.quizapp.sourcedata.SourceFactory;
import com.gwc.quizapp.sourcedata.SourceType;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class QuestionProvider {

    private final static String TAG = "QuestionProvider";
    private static final int QUESTION_THRESHOLD = 5;
    public static final int QUESTION_PAGE_SIZE = 10;
    private static boolean REPLENISH_QUESTIONS = false;
    private static QuestionProvider _instance;
    private Queue<Question> questionQueue = new ArrayBlockingQueue(QUESTION_PAGE_SIZE);
    private Source source;

    public Question getNextQuestion(){
        if(questionQueue.size() < QUESTION_THRESHOLD){
            REPLENISH_QUESTIONS = true;
        }
        return questionQueue.poll();
    }

    public int getCountOfQuestionsInQueue(){
        return questionQueue.size();
    }

    public synchronized static QuestionProvider get_instance(SourceType type){
        if(null == _instance){
            Log.i(TAG, "get_instance: questionprovider instance not present");
            _instance = new QuestionProvider(type);
        }

        Log.i(TAG, "get_instance: returning questionprovider instance");
        return _instance;
    }
    private QuestionProvider(SourceType type){
        Log.i(TAG, "QuestionProvider: creating provider for sourcetype "+type.name());

        source = SourceFactory.createSource(type);

        Log.d(TAG, "QuestionProvider: before loading questions");
        loadQuestions();
    }

    private void loadQuestions() {
        questionQueue.addAll(source.getListOfQuestions(QUESTION_PAGE_SIZE));
    }
}
