package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DisplayRecipes extends AppCompatActivity implements CustomAdapter.OnRecipeListener{
    private ArrayList<String> recipeNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipes);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.GET_USERNAME);

        recipeNames = new ArrayList<>();

        for(int i = 0; i < 25; i++){
            recipeNames.add(""+i);
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recipe_list);
        CustomAdapter adapter = new CustomAdapter(this, recipeNames, recipeNames, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRecipeClick(int position) {
        System.out.println(recipeNames.get(position));
//        recipeNames.get(position);
//        Intent intent = new Intent(this, NewActivity--);
//        startActivity(intent);
    }
}