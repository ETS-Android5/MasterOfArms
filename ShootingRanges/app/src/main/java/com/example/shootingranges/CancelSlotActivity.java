package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CancelSlotActivity extends AppCompatActivity {

    String id,name,email,slot;

    String time1,time2;
    int count2;

    TextView txtId,txtName,txtSlot;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_slot);
        setTitle("Slot Cancellation Screen");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db=new DatabaseHelper(this);

        Intent intent=getIntent();
        id=intent.getStringExtra("Id");
        name=intent.getStringExtra("Name");
        email=intent.getStringExtra("Email");
        slot=intent.getStringExtra("Slot");

        txtId=(TextView)findViewById(R.id.txtVwCancelId);
        txtName=(TextView)findViewById(R.id.ttxtVwCancelName);
        txtSlot=(TextView)findViewById(R.id.txtVwCancelSLot);

        txtId.setText(id);
        txtName.setText(name);
        txtSlot.setText(slot);
    }

    public void btncancel(View view)
    {
        Cursor res=db.getPlayerSlot(id);
        if(res.getCount()!=0) {
            while (res.moveToNext()) {
                time1 = res.getString(9);
            }
        }

        Cursor res1=db.getSlot(time1);
        if(res1.getCount()!=0) {
            while (res1.moveToNext()) {
                time2 = res1.getString(0);
                count2= res1.getInt(1);
            }
        }

            count2=count2+1;
            boolean up2=db.deleteSlot(id);

            boolean up=db.updateIncreaseSlot(time2,count2);
            if(up)
            {
                Toast.makeText(getApplicationContext(),"Slot Cancelled",Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {

                    public void run() {

                        try {

                            GmailSender sender = new GmailSender(

                                    "ptksinstitute@gmail.com",

                                    "Ptks@12345");


                            //sender.addAttachment(Environment.DIRECTORY_PICTURES.concat(path));

                            //Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_LONG).show();


                            sender.sendMail("Cancellation of Slot", "Slot Cancelled. \nTime : " + time1,

                                    "ssaipriyanka@gmail.com",

                                    email);

                            // Toast.makeText(getApplicationContext(),"Mail is sent",Toast.LENGTH_LONG).show();

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }

                    }

                }).start();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sorry",Toast.LENGTH_LONG).show();
            }
    }

    public void btnback(View view)
    {
        Intent intent = new Intent(CancelSlotActivity.this, MainMenuActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        finish();
    }
}
