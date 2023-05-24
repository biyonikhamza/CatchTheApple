package com.biyonikhamza.catchtheapple;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.Identity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeText;
    int result;
    TextView scoreText;
    TextView resultText;
    ImageView ivApple1;
    ImageView ivApple2;
    ImageView ivApple3;
    ImageView ivApple4;
    ImageView ivApple5;
    ImageView ivApple6;
    ImageView ivApple7;
    ImageView ivApple8;
    ImageView ivApple9;
    ImageView ivApple10;
    ImageView ivApple11;
    ImageView ivApple12;
    ImageView ivApple13;
    ImageView[] imageArray;
    android.os.Handler handler;
    Runnable runnable;
    SharedPreferences sharedPreferences;
    int score, lastScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startAlertDialogFun();

        result = 0;
        timeText = findViewById(R.id.time_board);
        scoreText = findViewById(R.id.score_board);
        resultText = findViewById(R.id.result_board);
        ivApple1 = findViewById(R.id.ivApple1);
        ivApple2 = findViewById(R.id.ivApple2);
        ivApple3 = findViewById(R.id.ivApple3);
        ivApple4 = findViewById(R.id.ivApple4);
        ivApple5 = findViewById(R.id.ivApple5);
        ivApple6 = findViewById(R.id.ivApple6);
        ivApple7 = findViewById(R.id.ivApple7);
        ivApple8 = findViewById(R.id.ivApple8);
        ivApple9 = findViewById(R.id.ivApple9);
        ivApple10 = findViewById(R.id.ivApple10);
        ivApple11 = findViewById(R.id.ivApple11);
        ivApple12 = findViewById(R.id.ivApple12);
        ivApple13 = findViewById(R.id.ivApple13);

        sharedPreferences = this.getSharedPreferences("com.biyonikhamza.catchtheapple" , MODE_PRIVATE);

        imageArray = new ImageView[] {
                ivApple1 , ivApple2 , ivApple3 ,
                ivApple4 , ivApple5 , ivApple6 ,
                ivApple7 , ivApple8 , ivApple9 ,
                ivApple10, ivApple11 , ivApple12 , ivApple13};

        score = 0;
        lastScore = sharedPreferences.getInt("storedInt" , 0);
        resultText.setText("Last Core : " + lastScore);
        hideImages();

    }
    public void hideImages (){
        for (ImageView image : imageArray) {
            image.setVisibility(View.INVISIBLE);
        }
    }
    public void clickedApple(View view){
        score++;
        scoreText.setText("Score : " + score);
        sharedPreferences.edit().putInt("storedInt" , score).apply();
    }

    public void showApple(){
        handler = new Handler();
        runnable = () ->{
            hideImages();
            Random random = new Random();
            int h = random.nextInt(13);
            imageArray[h].setVisibility(View.VISIBLE);
            handler.postDelayed(runnable , 400);
        };
        handler.post(runnable);
    }

    public void time(){
        new CountDownTimer(30000 , 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time : " + millisUntilFinished / 1000);
            }
            @Override
            public void onFinish() {
                timeText.setText("Bittiii !!");
                handler.removeCallbacks(runnable);
                hideImages();
                finishAlertDialog();
            }
        }.start();
    }


    public void startAlertDialogFun(){
        AlertDialog.Builder alertMessage = new AlertDialog.Builder(this);
        alertMessage.setTitle("Kıpkırmızı elmalar yakalama oyunumuza hoşgeldiniz.");
        alertMessage.setMessage("Oyanamak istersen butonlardan birini seç dostum. :)");
        alertMessage.setPositiveButton("Hadi Oynayalım broh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time();
                showApple();

            }
        });
        alertMessage.setNegativeButton("Başka Zamana İnşaallah :)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alertMessage.show();
    }

    public void finishAlertDialog(){
        AlertDialog.Builder alertMessage = new AlertDialog.Builder(this);
        alertMessage.setTitle("Nasıldı Eğlendin mi ?");
        alertMessage.setMessage("Tekrar denemek ister misin ? :)");
        alertMessage.setCancelable(false);
        alertMessage.setPositiveButton("Peki", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scoreText.setText("Score : 0");
                score = 0;
                time();
                showApple();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        alertMessage.setNegativeButton("Başka Zamana İnşaallah :)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertMessage.show();
    }
}