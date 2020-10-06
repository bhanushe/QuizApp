package com.gwc.quizapp.sourcedata;

import com.gwc.quizapp.model.Question;

import java.util.List;

public interface Source {

    abstract List<Question> getListOfQuestions(int count);
}
