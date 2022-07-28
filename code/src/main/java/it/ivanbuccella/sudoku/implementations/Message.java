package it.ivanbuccella.sudoku.implementations;

import java.io.Serializable;

public class Message implements Serializable {
    private String gameName;
    private Integer score;

    public Message(String gameName, Integer score) {
        this.gameName = gameName;
        this.score = score;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
