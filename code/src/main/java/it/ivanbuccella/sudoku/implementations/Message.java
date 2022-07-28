package it.ivanbuccella.sudoku.implementations;

import java.io.Serializable;

public class Message implements Serializable {
    private String gameName;
    private Integer score;
    private boolean isFinished;
    private User user;

    public Message(String gameName, Integer score, boolean isFinished, User user) {
        this.gameName = gameName;
        this.score = score;
        this.isFinished = isFinished;
        this.user = user;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage(Integer peerid) {
        String ret = "";

        if (this.score == 999)
            return ret;

        ret += "\n";

        ret += "Peer " + peerid + " [" + this.getUser().getNickName() + "] on game " + this.getGameName()
                + " has received "
                + this.getScore()
                + " score.\n";
        ret += "Peer " + peerid + " [" + this.getUser().getNickName() + "] has a total score of "
                + this.getUser().getScore()
                + ".\n";

        if (this.isFinished()) {
            ret += "Peer " + peerid + " [" + this.getUser().getNickName() + "] has finished the game "
                    + this.getGameName() + ".\n";
        }

        ret += "\n\n";

        return ret;
    }
}
