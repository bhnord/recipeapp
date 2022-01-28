package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeapp.data.Connection;
import com.example.recipeapp.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DisplayRecipes extends AppCompatActivity implements CustomAdapter.OnRecipeListener{
    private ArrayList<Recipe> recipes;
    private SwipeRefreshLayout refreshLayout;
    private CustomAdapter customAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipes);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.GET_USERNAME);


        //set up recyclerview with data from sql server
        recipes = new ArrayList<Recipe>();
        for(int i = 0; i < 25; i++){
            recipes.add(new Recipe(""+i, ""+i));
        }
        loadRecipes();

        recyclerView = findViewById(R.id.recipe_list);
        initRecyclerView();

        //set update from sql server on refresh
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //do something
                loadRecipes();
                Toast.makeText(getApplicationContext(), "Recipes refreshed", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView(){
        customAdapter = new CustomAdapter(this, recipes, this);
        recyclerView.setAdapter(customAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

    }



    private void loadRecipes(){
        String url = "http://130.215.220.234:8080/recipe?";
        url = url + "uid="+CurrentLogin.getUsername();
        System.out.println(url);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            recipes.clear();
            addFromJson(response);
            customAdapter.notifyDataSetChanged();
        }, error -> {

        });
        Connection.getInstance(this).addToRequestQueue(objectRequest);
    }

    private void addFromJson(JSONObject jsonObject){
        try {
            JSONArray arr = jsonObject.getJSONArray("rows");
            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                String owner = (String)obj.get("recipe_owner");
                String name = (String)obj.get("recipe_name");
                recipes.add(new Recipe(name, owner));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecipeClick(int position) {
        System.out.println(recipes.get(position));

//        recipes.get(position);
//        Intent intent = new Intent(this, NewActivity--);
//        startActivity(intent);
    }

    public void createRecipe(View view){
        Intent intent = new Intent(this, CreateRecipe.class);
        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Bitmap icon;
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;

                if(dX > 0){
                    p.setColor(Color.parseColor("#10a0e3"));
                    RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = drawableToBitmap(getResources().getDrawable(R.drawable.ic_baseline_share_24));
                    RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                } else {
                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = drawableToBitmap(getResources().getDrawable(R.drawable.ic_baseline_delete_24));
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT){
                Recipe r = recipes.get(viewHolder.getAdapterPosition());
                final String recipe_name = r.recipeName;
                final String recipe_owner = r.recipeOwner;
                String url = "http://130.215.220.234:8080/recipe?";

                url = url + "uid="+CurrentLogin.getUsername();
                url = url + "&recipe_owner="+r.recipeOwner;
                url = url + "&recipe_name="+r.recipeName;
                System.out.println(url);
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, response -> {
                    if(response.equals("completed")) {
                        recipes.remove(viewHolder.getAdapterPosition());
                        customAdapter.notifyDataSetChanged();
                    }
                    System.out.println(response);
                }, error -> {
                    System.out.println(error);
                });
                Connection.getInstance(DisplayRecipes.this).addToRequestQueue(stringRequest);

            }
            else if(direction == ItemTouchHelper.RIGHT){
                System.out.println("swiped left");
            }

        }


    };

    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}