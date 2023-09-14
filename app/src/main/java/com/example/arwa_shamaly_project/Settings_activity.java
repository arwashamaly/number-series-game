package com.example.arwa_shamaly_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arwa_shamaly_project.databinding.ActivitySettingsBinding;

public class Settings_activity extends AppCompatActivity {
ActivitySettingsBinding binding;
Database db;
String date;

    SharedPreferences sharedCheckBox;
    SharedPreferences.Editor editCheckBox;
    String failCheckBoxName ="checkBoxShared";
    String shared_CheckBox_Key = "checkBox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setTitle("Settings");

        db= new Database(getBaseContext());
        User user = db.getuser(Game_activity.username);
        int userId = user.getId();

        binding.btnShowAllGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),All_games_activity.class);
                startActivity(intent);
            }
        });
        binding.btnLastGameData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = db.getDate(userId);
                Toast.makeText(Settings_activity.this, ""+date, Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),Change_password_activity.class);
                startActivity(intent);
            }
        });
        binding.btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings_activity.this);
                builder.setTitle("Clear game history")
                        .setMessage("All game data will be deleted and start over.\n"
                                + "are you sure?")
                        .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteAllGameByID(userId);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings_activity.this);
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