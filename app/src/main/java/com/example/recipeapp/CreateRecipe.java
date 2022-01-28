package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.recipeapp.data.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateRecipe extends AppCompatActivity {
    private LinearLayout layoutList;
    private int numIngredients = 0;
    private ArrayList<View> ingredients = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        layoutList = findViewById(R.id.layout);
        addNewIngredient(layoutList);
    }

    public void addNewIngredient(View view){
        View ingredientView = getLayoutInflater().inflate(R.layout.layout_ingredientitem,null,false);
        layoutList.addView(ingredientView, 1+numIngredients++);
        ImageView imageView = ingredientView.findViewById(R.id.close_image);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeView(ingredientView);
            }
        });
    }


    public void sendRecipe(View view){
        String url = "http://130.215.220.234:8080/recipe?";
        url = url + "uid="+CurrentLogin.getUsername();
        System.out.println(url);
        JSONObject json = new JSONObject();
        JSONObject jsonArrayObj;
        JSONArray jsonArray = new JSONArray();
        try {
            json.put("recipe_owner", CurrentLogin.getUsername());
            json.put("recipe_name", ((EditText)findViewById(R.id.editTextRecipeTitle)).getText().toString());
            json.put("directions", ((EditText)findViewById(R.id.directions_box)).getText().toString());

            for(int i = 1; i < numIngredients+1; i++){
                View ingredient = layoutList.getChildAt(i);
                String name = ((EditText)ingredient.findViewById(R.id.edit_ingredient)).getText().toString();
                double qty = Double.parseDouble(((EditText)ingredient.findViewById(R.id.edit_num)).getText().toString());
                String unit = ((EditText)ingredient.findViewById(R.id.edit_unit)).getText().toString();
                jsonArrayObj = new JSONObject();
                jsonArrayObj.put("ingredient_name", name);
                jsonArrayObj.put("qty", qty);
                jsonArrayObj.put("unit", unit);
                jsonArray.put(jsonArrayObj);
            }
            json.put("ingredients", jsonArray);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, json, response -> {
            Intent intent = new Intent(CreateRecipe.this, DisplayRecipes.class);
            startActivity(intent);
        }, error -> {

        });
        Connection.getInstance(this).addToRequestQueue(objectRequest);
    }
    public void removeView(View view){
        layoutList.removeView(view);
        numIngredients--;
    }
}