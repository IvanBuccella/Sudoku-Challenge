package it.ivanbuccella.sudoku.implementations;

import net.tomp2p.peers.PeerAddress;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Sudoku implements Serializable {
    private String gameName;
    private HashSet<User> users;
    private Integer[][] board = {
            { 0, 6, 1, 7, 0, 4, 0, 5, 2 },
            { 3, 0, 2, 1, 0, 8, 0, 4, 9 },
            { 4, 9, 0, 0, 5, 0, 1, 0, 6 },
            { 0, 1, 8, 0, 7, 5, 6, 0, 4 },
            { 6, 7, 0, 3, 4, 0, 9, 2, 8 },
            { 9, 3, 4, 6, 8, 2, 0, 0, 7 },
            { 0, 0, 6, 8, 1, 9, 4, 0, 3 },
            { 7, 0, 3, 0, 2, 0, 8, 0, 1 },
            { 1, 8, 0, 4, 3, 7, 2, 6, 0 } };
    /*
     * 
     * private Integer[][] solvedBoard = {
     * { 8, 6, 1, 7, 9, 4, 3, 5, 2 },
     * { 3, 5, 2, 1, 6, 8, 7, 4, 9 },
     * { 4, 9, 7, 2, 5, 3, 1, 8, 6 },
     * { 2, 1, 8, 9, 7, 5, 6, 3, 4 },
     * { 6, 7, 5, 3, 4, 1, 9, 2, 8 },
     * { 9, 3, 4, 6, 8, 2, 5, 1, 7 },
     * { 5, 2, 6, 8, 1, 9, 4, 7, 3 },
     * { 7, 4, 3, 5, 2, 6, 8, 9, 1 },
     * { 1, 8, 9, 4, 3, 7, 2, 6, 5 } };
     */

    public void setBoard(Integer[][] board) {
        this.board = board;
    }

    public Sudoku(String gameName, HashSet<User> users) {
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

    public HashSet<User> getUsers() {
        return this.users;
    }

    public void setUsers(HashSet<User> users) {
        this.users = users;
    }

    public void removeUser(PeerAddress peerAddress) {
        if (this.users == null)
            return;

        User user = this.getUser(peerAddress);
        if (user == null)
            return;

        this.users.remove(user);
    }

    public void addUser(PeerAddress peerAddress, String nickName) {
        if (this.users == null)
            this.users = new HashSet<>();

        User user = this.getUser(peerAddress);
        if (user != null) {
            user.setNickName(nickName);
            return;
        }

        this.users.add(new User(peerAddress, nickName));
    }

    public User getUser(PeerAddress peerAddress) {
        if (this.users == null)
            this.users = new HashSet<>();

        for (User user : this.users) {
            if (user.getPeerAddress().equals(peerAddress))
                return user;
        }

        return null;
    }

    private boolean presentInRow(Integer value, Integer row) {
        Integer[] rowArray = this.board[row];
        for (Integer i = 0; i < rowArray.length; i++) {
            if (value.equals(rowArray[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean presentInCol(Integer value, Integer col) {
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

    private boolean presentInCell(Integer value, Integer col, Integer row) {
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
        boolean presentInCol = this.presentInCol(value, col);
        boolean presentInRow = this.presentInRow(value, row);
        boolean presentInCell = this.presentInCell(value, col, row);

        if (this.board[row][col].equals(value)) {
            return 0;
        }

        if (!presentInCol && !presentInRow && !presentInCell) {
            this.board[row][col] = value;
            return 1;
        }

        return -1;
    }

    public boolean isFinished() {
        for (Integer i = 0; i < 9; i++) {
            for (Integer j = 0; j < 9; j++) {
                if (this.board[i][j] == 0)
                    return false;
            }
        }

        for (Integer i = 0; i < 9; i++) {
            if (!this.validateRow(i))
                return false;
            if (!this.validateColumn(i))
                return false;
        }

        if (!this.validateCells())
            return false;

        return true;
    }

    private boolean validateRow(Integer row) {
        ArrayList<Integer> values = new ArrayList<>();
        for (Integer i = 0; i < 9; i++) {
            if (values.contains(this.board[row][i])) {
                return false;
            }
            values.add(this.board[row][i]);
        }
        return true;
    }

    private boolean validateColumn(Integer col) {
        ArrayList<Integer> values = new ArrayList<>();
        for (Integer i = 0; i < 9; i++) {
            if (values.contains(this.board[i][col])) {
                return false;
            }
            values.add(this.board[i][col]);
        }
        return true;
    }

    private boolean validateCells() {
        ArrayList<Integer> values;
        for (Integer row = 0; row < 9; row = row + 3) {
            for (Integer col = 0; col < 9; col = col + 3) {
                values = new ArrayList<>();
                for (Integer r = row; r < row + 3; r++) {
                    for (Integer c = col; c < col + 3; c++) {
                        if (values.contains(this.board[r][c])) {
                            return false;
                        }
                        values.add(this.board[r][c]);
                    }
                }
            }
        }
        return true;
    }
}