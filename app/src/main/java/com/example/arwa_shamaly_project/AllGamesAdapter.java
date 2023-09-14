package com.example.arwa_shamaly_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arwa_shamaly_project.databinding.AllGamesListBinding;

import java.util.ArrayList;

public class AllGamesAdapter extends RecyclerView.Adapter<AllGamesAdapter.GamesHolder> {
private ArrayList<Game> games;
private Context context;

    public AllGamesAdapter(ArrayList<Game> games, Context context) {
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public GamesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AllGamesListBinding binding = AllGamesListBinding.inflate(LayoutInflater.from(context),
                parent,false);
        return new GamesHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull GamesHolder holder, int position) {
        Game game = games.get(position);
        holder.binding.tvResult.setText(String.valueOf(game.getScore()));
        holder.binding.tvDate.setText(game.getDate());
        holder.binding.tvFullname.setText(game.getUserFullName());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class GamesHolder extends RecyclerView.ViewHolder{
        AllGamesListBinding binding;
        public GamesHolder(@NonNull View itemView) {
            super(itemView);
            binding = AllGamesListBinding.bind(itemView);

        }
    }
}
