package com.example.arwa_shamaly_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arwa_shamaly_project.databinding.ActivityAllGamesBinding;

import java.util.ArrayList;

public class All_games_activity extends AppCompatActivity {
ActivityAllGamesBinding binding;
Database db;
    SharedPreferences sharedCheckBox;
    SharedPreferences.Editor editCheckBox;
    String failCheckBoxName ="checkBoxShared";
    String shared_CheckBox_Key = "checkBox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllGamesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setTitle("All Games");

        db = new Database(getBaseContext());

        User user = db.getuser(Game_activity.username);
        int idUser = user.getId();

        ArrayList<Game> games = db.getGames(idUser);
        AllGamesAdapter adapter = new AllGamesAdapter(games,getBaseContext());
        binding.rvl.setAdapter(adapter);
        binding.rvl.setHasFixedSize(true);
        binding.rvl.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(All_games_activity.this);
                builder.setTitle("Log out")
                        .setMessage("Are you sure you want to log out of this account?")
                        .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedCheckBox= getSharedPreferences(failCheckBoxName,MODE_PRIVATE);
                                editCheckBox =sharedCheckBox.edit();
                                editCheckBox.putBoolean(shared_CheckBox_Key,false);
                                editCheckBox.apply();
                                finish();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
        }
        return false;
    }
}