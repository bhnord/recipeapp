package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    public static final String CREATE_LOGIN = "com.example.recipeapp.CREATELOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void createAccount(View view){
        //if not in databse -- return false
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.username_box);
//        String username = editText.getText().toString();
//        intent.putExtra(CREATE_LOGIN, username);
        startActivity(intent);
    }
}