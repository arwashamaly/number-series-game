package com.example.arwa_shamaly_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arwa_shamaly_project.databinding.ActivityChangePasswordBinding;

public class Change_password_activity extends AppCompatActivity {
ActivityChangePasswordBinding binding;
Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setTitle("Change password");

        db = new Database(getBaseContext());
        User user = db.getuser(Game_activity.username);
        int userId =user.getId();
        String pass = db.getPasswordByID(userId);

        binding.btnSaveNwePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass =binding.etOldPass.getText().toString();
                String newPass =binding.etNewPass.getText().toString();
                if (oldPass.equals(pass)){
                      boolean updatePass =db.updatePassword(newPass,userId);
                      if (updatePass==true){
                        Toast.makeText(getBaseContext(), "successfully done", Toast.LENGTH_SHORT).show();
                      binding.etOldPass.setText("");
                      binding.etNewPass.setText(""); }
                }else {
                    binding.etOldPass.setError("Please make sure the password is correct");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_pass_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}