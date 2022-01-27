package com.example.recipeapp.data;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeapp.DisplayRecipes;
import com.example.recipeapp.R;



public class Connection {
    private static  Context context;
    private RequestQueue requestQueue;
    private static Connection instance;

    private Connection(Context context){
        this.context = context;
    }

    public static synchronized Connection getInstance(Context context){
        if(instance==null){
            instance = new Connection(context);
        }
        return instance;
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }


//    public static boolean login(String uid, String password, Context context){
//        boolean res = false;
//        String p;
//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = "http://130.215.220.234:8080/login?";
//        url = url + "uid="+uid;
//        url = url + "&pass="+ password;
//        System.out.println(url);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
//            return false;
//        }, error -> {
//            System.out.println(error);
//        });
//        queue.add(stringRequest);
//    }

}
