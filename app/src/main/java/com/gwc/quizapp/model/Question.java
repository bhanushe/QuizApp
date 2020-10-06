package com.gwc.quizapp.model;

import java.util.List;
import java.util.Objects;

public class Question {
    private int id;
    private String question;
    private List<Answer> options;
    private int correctAnswerId;
    private String hint;

    public Question(int id, String question, List<Answer> options, int correctAnswerId) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswerId = correctAnswerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getOptions() {
        return options;
    }

    public void setOptions(List<Answer> options) {
        this.options = options;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", correctAnswerId=" + correctAnswerId +
                ", hint='" + hint + '\'' +
                '}';
    }
}
