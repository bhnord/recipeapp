package com.example.recipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.data.Recipe;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<Recipe> recipes;
    private Context context;
    private OnRecipeListener onRecipeListener;

    public CustomAdapter(Context context, ArrayList<Recipe> recipes, OnRecipeListener onRecipeListener){
        this.recipes = recipes;
        this.context = context;
        this.onRecipeListener = onRecipeListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipeName;
        TextView recipeOwner;
        OnRecipeListener onRecipeListener;
        public ViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeOwner = itemView.findViewById(R.id.recipeOwner);
            this.onRecipeListener = onRecipeListener;
            itemView.setClipToOutline(true);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_listitem, viewGroup, false);

        return new ViewHolder(view, onRecipeListener);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.getTextView().setText(localDataSet[position]);

        viewHolder.recipeName.setText(recipes.get(position).recipeName);
        viewHolder.recipeOwner.setText(recipes.get(position).recipeOwner);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface OnRecipeListener{
        void onRecipeClick(int position);
    }


}
