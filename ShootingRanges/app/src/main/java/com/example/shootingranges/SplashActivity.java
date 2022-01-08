package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static ImageView master,of,arms;
    private static Animation animLeftToRight,animRightToLeft,animSlideUp;

    SharedPreferences sharedPreferences;
    Boolean isSaved = false;
    Intent intent;


    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String EmailAdd="emailKey";
    public static final String Password="passKey";
    public static final String isDataSaved="saveKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle("Splash Screen");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        isSaved=sharedPreferences.getBoolean(isDataSaved,false);

        master=(ImageView)findViewById(R.id.imgVwMaster) ;
        of=(ImageView)findViewById(R.id.imgVwOf) ;
        arms=(ImageView)findViewById(R.id.imgVwArms) ;

        animLeftToRight= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left_to_right);
        master.startAnimation(animLeftToRight);

        animRightToLeft= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_to_left);
        of.startAnimation(animRightToLeft);

        animSlideUp= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        arms.startAnimation(animSlideUp);

        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isSaved)
                {

                    intent = new Intent(SplashActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3050);
    }
}
