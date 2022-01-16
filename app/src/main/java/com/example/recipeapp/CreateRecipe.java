package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CreateRecipe extends AppCompatActivity {
    private LinearLayout layoutList;
    private int numIngredients = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        layoutList = findViewById(R.id.layout);
        addNewIngredient(layoutList);
    }

    public void addNewIngredient(View view){
        View ingredientView = getLayoutInflater().inflate(R.layout.layout_ingredientitem,null,false);
        layoutList.addView(ingredientView, numIngredients++);
        ImageView imageView = ingredientView.findViewById(R.id.close_image);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeView(ingredientView);
            }
        });
    }
    public void removeView(View view){
        layoutList.removeView(view);
        numIngredients--;
    }
}