package com.example.recipeapp;


public class CurrentLogin {
    private static String username = "";

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentLogin.username = username;
    }

}
