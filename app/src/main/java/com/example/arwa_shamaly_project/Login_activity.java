package com.example.arwa_shamaly_project;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.arwa_shamaly_project.databinding.ActivityLoginBinding;

public class Login_activity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Database db;
    Uri uri;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String usernameShared ="usernameShared";
    String shared_UserName_Key = "userName";

    SharedPreferences sharedCheckBox;
    SharedPreferences.Editor editCheckBox;
    String failCheckBoxName ="checkBoxShared";
    String shared_CheckBox_Key = "checkBox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences(usernameShared,MODE_PRIVATE);
        editor = sp.edit();

        sharedCheckBox= getSharedPreferences(failCheckBoxName,MODE_PRIVATE);
        editCheckBox =sharedCheckBox.edit();

        ActivityResultLauncher<String> al1 = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        uri = result;
                        binding.imgLoginUser.setImageURI(result);
                    }
                });

        binding.imgLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al1.launch("image/*");
            }
        });

        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(binding.checkBox.isChecked()){
                    editCheckBox.putBoolean(shared_CheckBox_Key,true);
                    editCheckBox.apply();
                }else {
                    editCheckBox.putBoolean(shared_CheckBox_Key,false);
                    editCheckBox.apply();
                }
            }
        });


        //استقبال البيانات و عرضها في الحقول
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            String uriString = intent.getStringExtra("uri");
            if (!TextUtils.isEmpty(uriString)){
                uri = Uri.parse(uriString);
            }
            binding.etUsername.setText(username);
            binding.etPassword.setText(password);
            binding.imgLoginUser.setImageURI(uri);
        }

        db = new Database(getBaseContext());

        //التحقق من البيانات و الانتقال الى شاشة اللعبة
        binding.btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                boolean checkuserpass = db.checkusernamepassword(username, password);

                if (!username.isEmpty() && !password.isEmpty() && checkuserpass == true) {
                    Intent intent = new Intent(getBaseContext(), Game_activity.class);
                    editor.putString(shared_UserName_Key,username);
                    editor.apply();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login_activity.this, "Please verify the information", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}