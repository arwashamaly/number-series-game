package com.example.arwa_shamaly_project;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.arwa_shamaly_project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    SharedPreferences sharedCheckBox;
    SharedPreferences.Editor editCheckBox;
    String failCheckBoxName ="checkBoxShared";
    String shared_CheckBox_Key = "checkBox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedCheckBox= getSharedPreferences(failCheckBoxName,MODE_PRIVATE);
        editCheckBox = sharedCheckBox.edit();

        boolean checkBox = sharedCheckBox.getBoolean(shared_CheckBox_Key,false);

        if (checkBox==true){
            Intent intent = new Intent(getBaseContext(),Game_activity.class);
            startActivity(intent);}

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),Login_activity.class);
                startActivity(intent);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),Register_activity.class);
                startActivity(intent);
            }
        });

    }
}