package com.example.shootingranges;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends AppCompatActivity {

    DatabaseHelper db;

    public String id,name,email,phone;
    TextView path;

    final int RQS_LOADIMAGE=0;
    final int RQS_SENDEMAIL=1;

    Uri imgUri=null;

    Intent intent;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        setTitle("Upload Certificate Screen");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db=new DatabaseHelper(this);

        path=(TextView)findViewById(R.id.txtVwPath);

        intent=getIntent();
        id=intent.getStringExtra("Id");
        name=intent.getStringExtra("Name");
        email=intent.getStringExtra("Email");
        phone=intent.getStringExtra("Phone");
    }

    public void browse(View view)
    {
        intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,RQS_LOADIMAGE);
    }

    public void upload(View view)
    {
        count=1;

        Boolean course = db.updateAdvData(id);
        if(path.getText().toString().equals("File Path"))
        {
            Toast.makeText(getApplicationContext(),"Please attach certificate",Toast.LENGTH_LONG).show();

        }
        else
        {
            String emailAddress="ptksinstitute@gmail.com";
            String emailAdressList[]={emailAddress};
            intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL,emailAdressList);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Certificate");

            if(imgUri!=null)
            {
                intent.putExtra(intent.EXTRA_STREAM,imgUri);
                intent.setType("image/*");
            }
            else
            {
                intent.setType("plain/text");
            }

            startActivity(Intent.createChooser(intent,"Choice App To Send Email"));

            Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_LONG).show();

        }
    }

    public void next(View view)
    {
        if(!path.getText().toString().equals("File Path"))
        {
            if (count == 1)
            {
                new Thread(new Runnable() {

                    public void run() {

                        try {

                            GmailSender sender = new GmailSender(

                                    "ptksinstitute@gmail.com",

                                    "Ptks@12345");


                            //sender.addAttachment(Environment.DIRECTORY_PICTURES.concat(path));

                            //Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_LONG).show();


                            sender.sendMail("Confirmation Enrollment of Advance Course", "Successfully applied for the Advance Cousre. ",

                                    "ssaipriyanka@gmail.com",

                                    email);

                            // Toast.makeText(getApplicationContext(),"Mail is sent",Toast.LENGTH_LONG).show();

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }

                    }

                }).start();


                intent = new Intent(UploadActivity.this, AdvanceActivity2.class);
                intent.putExtra("Id", id);
                intent.putExtra("Name", name);
                intent.putExtra("Phone", phone);
                startActivity(intent);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please upload certificate through mail", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode)
            {
                case RQS_LOADIMAGE:
                    imgUri=data.getData();
                    path.setText(imgUri.toString());
                    break;
                case RQS_SENDEMAIL:
                    break;
            }
        }
    }
}
