package com.example.arwa_shamaly_project;

public class Game {
    private int id_game;
    private int id_user;
    private String userFullName;
    private int score;
    private String date;



    public Game() {
    }
    public Game( int score, String date,String userFullName) {
        this.userFullName = userFullName;
        this.score = score;
        this.date = date;
    }
    public Game(int id_user, String userFullName, int score, String date) {
        this.id_user = id_user;
        this.userFullName = userFullName;
        this.score = score;
        this.date = date;
    }

    public Game(int id_game, int id_user, int score, String date) {
        this.id_game = id_game;
        this.id_user = id_user;
        this.score = score;
        this.date = date;
    }

    public Game(int score, String date,int id_user) {
        this.id_user = id_user;
        this.score = score;
        this.date = date;
    }

    public Game(int score, String date) {
        this.score = score;
        this.date = date;
    }

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
