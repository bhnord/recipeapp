package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeapp.data.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    public static final String GET_USERNAME = "com.example.recipeapp.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void login(View view){
//        Intent intent = new Intent(this, DisplayRecipes.class);
//        EditText editText = (EditText) findViewById(R.id.username_box);
//        String username = editText.getText().toString();
//        intent.putExtra(GET_USERNAME, username);
//        startActivity(intent);
        try {
            getLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //change this for more security
    public void getLogin() throws Exception{
        String url = "http://130.215.220.234:8080/login?";
        final String uid = ((EditText)findViewById(R.id.username_box)).getText().toString();
        final String pass = ((EditText)findViewById(R.id.password_box)).getText().toString();
        url = url + "uid="+uid;
        url = url + "&pass="+pass;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            if(response.equals("valid login")) {
                CurrentLogin.setUsername(uid);
                Intent intent = new Intent(this, DisplayRecipes.class);
                startActivity(intent);
            }
            System.out.println(response);
            }, error -> {
            System.out.println(error);
        });
        Connection.getInstance(this).addToRequestQueue(stringRequest);
        //USE FOR POST ONLY -- GET SHOULDN'T HAVE BODY
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("uid", "bnord");
//        jsonObject.put("pass", "password");
//        final String body = jsonObject.toString();
//        System.out.println(body.getBytes(StandardCharsets.UTF_8));
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String response) {
//                System.out.println("out "+response);
//            }
//        }, error -> {System.out.println("error "+error);}){
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError{
//                    return body.getBytes(StandardCharsets.UTF_8);
//            }
//        };
//        queue.add(stringRequest);
    }

    public void goToAccountCreation(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}