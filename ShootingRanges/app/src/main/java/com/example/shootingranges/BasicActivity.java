package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BasicActivity extends AppCompatActivity {

    public String id,name,email,phone;
    Button apply;

    int minteger = 1;

    //final int SEND_SMS_PERMISSION_REQUEST_CODE=1;

    DatabaseHelper db;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        setTitle("Basic Course Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db=new DatabaseHelper(this);

        Intent intent=getIntent();
        id=intent.getStringExtra("Id");
        name=intent.getStringExtra("Name");
        email=intent.getStringExtra("Email");
        phone=intent.getStringExtra("Phone");


        apply=(Button)findViewById(R.id.btnApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //apply.setEnabled(false);
        /*if(checkPermission(Manifest.permission.SEND_SMS))
        {
            apply.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }*/

                //public void basicCourse(View view)
                //{
        minteger = minteger + 1;
        if(minteger>2)
        {
            Toast.makeText(getApplicationContext(), "Already Applied for the Basic Course!!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BasicActivity.this, BasicActivity2.class);
            intent.putExtra("Id", id);
            intent.putExtra("Name", name);
            intent.putExtra("Phone", phone);
            startActivity(intent);
        }

        else
            {
                Boolean course = db.updateData(id);
                if (course == true) {


                    /*if(checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsManager = SmsManager.getDefault();*/


                    // Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();


                    //Email Code


                    new Thread(new Runnable() {

                     public void run() {

                    try {

                        GmailSender sender = new GmailSender(

                                "ptksinstitute@gmail.com",

                                "Ptks@12345");


                        //sender.addAttachment(Environment.DIRECTORY_PICTURES.concat(path));

                        //Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_LONG).show();


                        sender.sendMail("Confirmation Enrollment of Basic Course", "Successfully applied for the Basic Cousre. ",

                                "ssaipriyanka@gmail.com",

                                email);

                        // Toast.makeText(getApplicationContext(),"Mail is sent",Toast.LENGTH_LONG).show();

                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }

                    }

                    }).start();
                    Intent intent = new Intent(BasicActivity.this, BasicActivity2.class);
                    intent.putExtra("Id", id);
                    intent.putExtra("Name", name);
                    intent.putExtra("Phone", phone);
                    //smsManager.sendTextMessage(phone,null,"PTKS Institute\nApplied Successfully for Basic Course.",null,null);
                    startActivity(intent);


                    }

                   else
                    {
                        //Toast.makeText(this,"Permission sent",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Cant send mail",Toast.LENGTH_LONG).show();
                    }
                    //  }

                }
            }
        });
    }
    }

 /*   public boolean checkPermission(String permission)
    {
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);

    }*/
//}

