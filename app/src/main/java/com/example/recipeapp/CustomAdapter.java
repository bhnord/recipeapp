package com.example.recipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private final ArrayList<String> recipeAuthor;
    private ArrayList<String> recipeNames;
    private Context context;
    private OnRecipeListener onRecipeListener;

    public CustomAdapter(Context context, ArrayList<String> recipeNames, ArrayList<String> recipeAuthor, OnRecipeListener onRecipeListener){
        this.recipeNames = recipeNames;
        this.recipeAuthor = recipeAuthor;
        this.context = context;
        this.onRecipeListener = onRecipeListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text;
        OnRecipeListener onRecipeListener;
        public ViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);
            text = itemView.findViewById(R.id.textView);
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

        viewHolder.text.setText(recipeNames.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeNames.size();
    }

    public interface OnRecipeListener{
        void onRecipeClick(int position);
    }


}
