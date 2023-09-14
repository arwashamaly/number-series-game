package com.example.arwa_shamaly_project;

import android.net.Uri;

public class User {
    private int id;
    private String full_name ;
    private String uri ;
    private String email ;
    private String username ;
    private String password ;
    private String country ;
    private String birthdate ;
    private int gender ;
    private int yearOfBirth;

    public User (){

    }

    public User(String full_name, String uri, String email, String username,
       String password, String country, String birthdate, int gender, int yearOfBirth) {
        this.full_name = full_name;
        this.uri = uri;
        this.email = email;
        this.username = username;
        this.password = password;
        this.country = country;
        this.birthdate = birthdate;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
    }

    public User(int id, String full_name, String username, int yearOfBirth) {
        this.id = id;
        this.full_name = full_name;
        this.username = username;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
