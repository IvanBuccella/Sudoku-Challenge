package it.ivanbuccella.sudoku.implementations;

import java.io.Serializable;
import net.tomp2p.peers.PeerAddress;

public class User implements Serializable {
    private PeerAddress peerAddress;
    private String nickName;
    private Integer score;

    public User(PeerAddress peerAddress, String nickName) {
        this.peerAddress = peerAddress;
        this.nickName = nickName;
        this.score = 0;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public PeerAddress getPeerAddress() {
        return this.peerAddress;
    }

    public void setPeerAddress(PeerAddress peerAddress) {
        this.peerAddress = peerAddress;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void addScore(Integer value) {
        this.score += value;
    }
}
