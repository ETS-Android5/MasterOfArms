package com.example.shootingranges;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Intent intent;
    public static EditText edtTxtEmail;
    public static EditText edtTxtPass;
    public static CheckBox chkBox;
    public static Button view;
    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String EmailAdd="emailKey";
    public static final String Password="passKey";
    public static final String isDataSaved="saveKey";

    DatabaseHelper db;

    public String id,name,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Page");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db=new DatabaseHelper(this);
    }


    public void Login(View view)
    {

        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        edtTxtEmail = (EditText) findViewById(R.id.edtTxtEmail);
        edtTxtPass = (EditText) findViewById(R.id.edtTxtPassword);
        chkBox=(CheckBox)findViewById(R.id.chkBoxRemember);

        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        String email=edtTxtEmail.getText().toString();
        String pass=edtTxtPass.getText().toString();


        if(chkBox.isChecked()) {

            editor.putString(EmailAdd,email);
            editor.putString(Password,pass);
            editor.putBoolean(isDataSaved,true);
            editor.commit();
        }


        if(!email.isEmpty())
        {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                if(email.length()>=5 && email.length()<=45)
                {
                    if (!pass.isEmpty()) {
                        if (pass.length() >= 5 && pass.length() <= 15) {

                            Boolean chkemailpassword=db.chkemailpassword(email,pass);
                            if(chkemailpassword==true)
                            {
                                Cursor res=db.getData(email);
                                if(res.getCount()!=0) {
                                    while (res.moveToNext()) {
                                        id=String.valueOf((res.getInt(0)));
                                        name=res.getString(1);
                                        phone=res.getString(5);

                                        intent = new Intent(LoginActivity.this, MenuActivity.class);
                                        intent.putExtra("Email",email);
                                        intent.putExtra("Id",id);
                                        intent.putExtra("Name",name);
                                        intent.putExtra("Phone",phone);
                                        startActivity(intent);
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Login Failed!!!",Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            edtTxtPass.setError("Password range is 5-15");
                        }
                    }
                    else
                    {
                        edtTxtPass.setError("Enter Password");
                    }
                }
                else
                {
                    edtTxtEmail.setError("Email Address range is 5-45");
                }
            }
            else {
                edtTxtEmail.setError("Not a valid Email Address ");
            }
        }
        else
        {
            edtTxtEmail.setError("Enter an Email Address");
        }

    }

    public void SignUp(View view)
    {
        intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Pop Up");
        builder.setMessage("Do you want to exit ?");
        builder.setPositiveButton("YES.EXIT NOW!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }


}
