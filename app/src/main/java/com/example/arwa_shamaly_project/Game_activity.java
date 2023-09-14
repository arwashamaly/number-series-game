package com.example.arwa_shamaly_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arwa_shamaly_project.databinding.ActivityGameBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Game_activity extends AppCompatActivity {
    ActivityGameBinding binding;
    MediaPlayer mediaPlayer;
    String [][] data;
    int hiddenNumber;
    Database db;
    int userId;
    String fullName;
    int yearOfBirth;
    int age;
    public static String username;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String sharedName ="usernameShared";
    String shared_UserName_Key = "userName";

    SharedPreferences sharedCheckBox;
    SharedPreferences.Editor editCheckBox;
    String failCheckBoxName ="checkBoxShared";
    String shared_CheckBox_Key = "checkBox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new Database(getBaseContext());

        setSupportActionBar(binding.toolbar);
        setTitle("Games");

        sp = getSharedPreferences(sharedName,MODE_PRIVATE);
        editor = sp.edit();
        username=sp.getString(shared_UserName_Key,"");

        sharedCheckBox= getSharedPreferences(failCheckBoxName,MODE_PRIVATE);
        editCheckBox =sharedCheckBox.edit();

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        User user = db.getuser(username);
        if (user!=null){
            userId =user.getId();
            fullName =user.getFull_name();
            yearOfBirth=user.getYearOfBirth();
            age=year-yearOfBirth;
            int finalScore=db.getFinalScore(userId);
            binding.tvGamesFullname.setText(fullName);
            binding.tvAge.setText(String.valueOf(age));
            binding.tvScore.setText(String.valueOf(finalScore));
        }

        Question question = Util.generteQuestion();
        data = question.getData();
        hiddenNumber = question.getHiddenNumber();

        binding.tvNum1.setText(data[0][0]);
        binding.tvNum2.setText(data[0][1]);
        binding.tvNum3.setText(data[0][2]);
        binding.tvNum4.setText(data[1][0]);
        binding.tvNum5.setText(data[1][1]);
        binding.tvNum6.setText(data[1][2]);
        binding.tvNum7.setText(data[2][0]);
        binding.tvNum8.setText(data[2][1]);
        binding.tvNum9.setText(data[2][2]);

        Log.d("aaa",hiddenNumber+"");

        binding.btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Question question = Util.generteQuestion();
                data = question.getData();
                hiddenNumber = question.getHiddenNumber();

                binding.tvNum1.setText(data[0][0]);
                binding.tvNum2.setText(data[0][1]);
                binding.tvNum3.setText(data[0][2]);
                binding.tvNum4.setText(data[1][0]);
                binding.tvNum5.setText(data[1][1]);
                binding.tvNum6.setText(data[1][2]);
                binding.tvNum7.setText(data[2][0]);
                binding.tvNum8.setText(data[2][1]);
                binding.tvNum9.setText(data[2][2]);
                Log.d("aaa",hiddenNumber+"");

            }
        });

        binding.btnCheckAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String answer =binding.etAnswer.getText().toString();
                if (!answer.isEmpty()){
                    if (answer.equals(String.valueOf(hiddenNumber))){

                        mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.true_answer);
                        int old_score = Integer.parseInt(binding.tvScore.getText().toString());
                        int new_score =old_score+10;
                        View v =getLayoutInflater().inflate(R.layout.toast_true_answer,null);
                        Toast toast =new Toast(getBaseContext());
                        toast.setView(v);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        mediaPlayer.start();
                        binding.tvScore.setText(String.valueOf(new_score));

                        Calendar now = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String time =sdf.format(now.getTime());
                        Game game = new Game(userId,fullName,new_score,time);
                        db.insertGame(game);

                        Question question = Util.generteQuestion();
                        data = question.getData();
                        hiddenNumber = question.getHiddenNumber();

                        binding.tvNum1.setText(data[0][0]);
                        binding.tvNum2.setText(data[0][1]);
                        binding.tvNum3.setText(data[0][2]);
                        binding.tvNum4.setText(data[1][0]);
                        binding.tvNum5.setText(data[1][1]);
                        binding.tvNum6.setText(data[1][2]);
                        binding.tvNum7.setText(data[2][0]);
                        binding.tvNum8.setText(data[2][1]);
                        binding.tvNum9.setText(data[2][2]);
                        binding.etAnswer.setText("");
                        Log.d("aaa",hiddenNumber+"");


                    }else {
                        mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.false_answer);
                        View v =getLayoutInflater().inflate(R.layout.toast_false_answer,null);
                        Toast toast =new Toast(getBaseContext());
                        toast.setView(v);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        mediaPlayer.start();

                        int currentScore=Integer.parseInt(binding.tvScore.getText().toString());
                        Calendar now = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String time =sdf.format(now.getTime());
                        Game game = new Game(userId,fullName,currentScore,time);
                        db.insertGame(game);
                        Log.d("aaa",hiddenNumber+"");
                    }
                }else {
                    binding.etAnswer.setError("Enter the answer please");
                }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(getBaseContext(),Settings_activity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(Game_activity.this);
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
