package it.ivanbuccella.sudoku.implementations;

import net.tomp2p.peers.PeerAddress;
import java.io.Serializable;
import java.util.HashSet;

public class Sudoku implements Serializable {
    private String gameName;
    private HashSet<PeerAddress> users;
    private Integer[][] board = {
            { 3, 0, 6, 5, 0, 8, 4, 0, 0 }, { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 8, 7, 0, 0, 0, 0, 3, 1 }, { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
            { 9, 0, 0, 8, 6, 3, 0, 0, 5 }, { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
            { 1, 3, 0, 0, 0, 0, 2, 5, 0 }, { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
            { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };

    public void setBoard(Integer[][] board) {
        this.board = board;
    }

    public Sudoku(String gameName, HashSet<PeerAddress> users) {
        this.gameName = gameName;
        this.users = users;
    }

    public Integer[][] getBoard() {
        return this.board;
    }

    public String getGameName() {
        return this.gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public HashSet<PeerAddress> getUsers() {
        return this.users;
    }

    public void setUsers(HashSet<PeerAddress> users) {
        this.users = users;
    }

    public void removeUser(PeerAddress peerAddress) {
        if (this.users != null)
            this.users.remove(peerAddress);
    }

    public void addUser(PeerAddress peerAddress) {
        if (this.users == null)
            this.users = new HashSet<>();
        this.users.add(peerAddress);
    }

    public boolean presentInRow(Integer value, Integer row) {
        Integer[] rowArray = this.board[row];
        for (Integer i = 0; i < rowArray.length; i++) {
            if (value.equals(rowArray[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean presentInCol(Integer value, Integer col) {
        Integer[] columnArray = new Integer[9];
        for (Integer i = 0; i < 9; i++) {
            columnArray[i] = this.board[i][col];
        }
        for (Integer i = 0; i < 9; i++) {
            if (value.equals(columnArray[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean presentInCell(Integer value, Integer col, Integer row) {
        Integer[] cellArray = new Integer[9];
        Integer celCol = col / 3;
        Integer celRow = row / 3;
        for (Integer i = 0; i < 3; i++) {
            for (Integer j = 0; j < 3; j++) {
                cellArray[i * 3 + j] = this.board[celRow * 3 + i][celCol * 3 + j];
            }
        }
        for (Integer i = 0; i < cellArray.length; i++) {
            if (value.equals(cellArray[i])) {
                return true;
            }
        }
        return false;
    }

    public Integer add(Integer row, Integer col, Integer value) {
        Integer score = -1;

        if (!this.presentInCol(value, col) &&
                !this.presentInRow(value, row) &&
                !this.presentInCell(value, col, row)) {

            if (this.board[row][col] == 0)
                score = 1;
            else
                score = 0;

            this.board[row][col] = value;
        }

        return score;
    }

}