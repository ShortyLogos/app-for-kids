package com.dm.tpfinal;

public class Question {
    private String question;
    private boolean completee;

    public Question(String question, boolean completee) {
        this.question = question;
        this.completee = completee;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isCompletee() {
        return completee;
    }

    public void setCompletee(boolean completee) {
        this.completee = completee;
    }
}
