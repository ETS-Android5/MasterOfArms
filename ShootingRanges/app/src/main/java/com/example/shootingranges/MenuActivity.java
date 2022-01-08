package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.shootingranges.LoginActivity.chkBox;
import static com.example.shootingranges.LoginActivity.edtTxtEmail;
import static com.example.shootingranges.LoginActivity.edtTxtPass;


public class MenuActivity extends AppCompatActivity {

    private Button logout;
    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String EmailAdd = "emailKey";
    public static final String isDataSaved = "saveKey";

    public String email,id, name, phone, course;

    DatabaseHelper db;

    int minteger = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu Page");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db = new DatabaseHelper(this);


        logout = (Button) findViewById(R.id.btnLogout);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String Email = sharedPreferences.getString(EmailAdd, "");

        Intent intent = getIntent();
        email=intent.getStringExtra("Email");
        id = intent.getStringExtra("Id");
        name = intent.getStringExtra("Name");
        phone = intent.getStringExtra("Phone");

    }


    public void logoutClick(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isDataSaved, false);
        editor.clear();
        editor.commit();

        edtTxtEmail.setText("");
        edtTxtPass.setText("");
        chkBox.setChecked(false);
        finish();
    }

    public void mainMenu(View view) {
        Intent intent = new Intent(MenuActivity.this, MainMenuActivity.class);
        intent.putExtra("Id", id);
        intent.putExtra("Name", name);
        intent.putExtra("Email",email);
        intent.putExtra("Phone", phone);
        startActivity(intent);
    }


    public void advance(View view) {
        Cursor res = db.getAdvCourse(id);
        //while (res.moveToNext()) {
        if(res.moveToFirst()){
            course = res.getString(res.getColumnIndex("ADV"));
                Intent intent = new Intent(MenuActivity.this, AdvanceActivity.class);
                intent.putExtra("Id", id);
                intent.putExtra("Name", name);
                intent.putExtra("Email",email);
                intent.putExtra("Phone", phone);
                startActivity(intent);
        }
    }


    public void basic(View view) {
        Button btnbasic = (Button) findViewById(R.id.btnBasic);

        Cursor res = db.getCourse(id);
            //while (res.moveToNext()) {
        if(res.moveToFirst()){
                course = res.getString(res.getColumnIndex("COURSE"));

                if (course==null) {
                    Intent intent = new Intent(MenuActivity.this, BasicActivity.class);
                    intent.putExtra("Id", id);
                    intent.putExtra("Name", name);
                    intent.putExtra("Email",email);
                    intent.putExtra("Phone", phone);
                    startActivity(intent);

                   } else {
                    Intent intent = new Intent(MenuActivity.this, BasicActivity2.class);
                    intent.putExtra("Id", id);
                    intent.putExtra("Name", name);
                    intent.putExtra("Phone", phone);
                    startActivity(intent);

                }
        }
        }
}