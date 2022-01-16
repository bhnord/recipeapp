package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String GET_USERNAME = "com.example.recipeapp.USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        Intent intent = new Intent(this, DisplayRecipes.class);
        EditText editText = (EditText) findViewById(R.id.username_box);
        String username = editText.getText().toString();
        intent.putExtra(GET_USERNAME, username);
        startActivity(intent);
    }

    public void goToAccountCreation(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}