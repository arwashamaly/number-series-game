package com.example.arwa_shamaly_project;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.arwa_shamaly_project.databinding.ActivityRegisterBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Register_activity extends AppCompatActivity {
ActivityRegisterBinding binding;
Database db ;
Uri uri ;
int yearOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //اختيار صورة
        ActivityResultLauncher<String> al1 = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                uri = result;
                binding.imgRegisterUser.setImageURI(result);
            }
        });
        binding.imgRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al1.launch("image/*");
            }
        });

        //تحديد تاريخ الميلاد
        binding.tvBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //جلب التاريخ الحالي و جعله الافتراضي
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                binding.tvBirthdate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                                yearOfBirth=year;
                                //حساب العمر
                                //now.get(Calendar.YEAR)-year;
                            }
                        },
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");

            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name=binding.etFullname.getText().toString();
                String email=binding.etEmail.getText().toString();
                String username=binding.etRegisterUsername.getText().toString();
                String password=binding.etRegisterPassword.getText().toString();
                String re_password=binding.etRegisterRePass.getText().toString();
                String country=binding.spCountry.getSelectedItem().toString();
                String birthdate=binding.tvBirthdate.getText().toString();

                String uriString = uri.toString();

                int gender =-1;
                if (binding.rbFemale.isChecked()){
                    gender=1; //انثى
                }else {
                    gender=0; //ذكر
                }
                
                //انشاء الداتابيز لو مش موجودة , و لو موجودة جيب مؤشر عليها
                db = new Database(getBaseContext());
                
                //عمل اوبجت لارساله
                User user = new User(full_name,uriString,email,username,password,country,birthdate,gender,yearOfBirth);

                if (!full_name.isEmpty()&&!username.isEmpty()&&!uriString.isEmpty()
                        && !birthdate.isEmpty()){
                    if (password.equals(re_password)){
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            boolean checkusername =db.checkusername(username);
                            if (checkusername==false){
                                //ارسال الاوبجكت
                                boolean insertdata= db.insertUsers(user);
                                if (insertdata==true){
                                    Toast.makeText(Register_activity.this, "insert completed", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(),Login_activity.class);
                                    intent.putExtra("username",username);
                                    intent.putExtra("password",password);
                                    intent.putExtra("uri",uriString);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(Register_activity.this, "null", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Register_activity.this, "Username already exists !", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Register_activity.this, "Check that the email is written correctly", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Register_activity.this, "Check password matches", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Register_activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}