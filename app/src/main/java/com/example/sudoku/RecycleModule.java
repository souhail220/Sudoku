package com.example.sudoku;

public class RecycleModule {
    String userName;
    String score;
    //int image;

    public RecycleModule(String userName, String score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public String getScore() {
        return score;
    }

    /*public int getImage() {
        return image;
    }*/


}
