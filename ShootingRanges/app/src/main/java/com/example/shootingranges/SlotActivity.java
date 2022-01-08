package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shootingranges.Adapter.LaneAdapter;
import com.example.shootingranges.Adapter.SlotAdapter;

import java.lang.reflect.Field;

public class SlotActivity extends AppCompatActivity {

    Spinner slotSpin;
    int index1;
    String[] slots={"Select","7am-9am","9am-11am","11am-1pm","1pm-3pm","3pm-5pm","5pm-7pm","7pm-9pm"};

    RelativeLayout relativeLayout;

    public String id,name,email,phone;

    public String time;
    public int count;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);
        setTitle("Slot Booking Screen");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db=new DatabaseHelper(this);

        Intent intent=getIntent();
        id=intent.getStringExtra("Id");
        name=intent.getStringExtra("Name");
        email=intent.getStringExtra("Email");
        phone=intent.getStringExtra("Phone");

        slotSpin=(Spinner)findViewById(R.id.slotSpinner);

        slotSpin=(Spinner)findViewById(R.id.slotSpinner);

        SlotAdapter slotAdapter=new SlotAdapter(getApplicationContext(),slots);
        slotSpin.setAdapter(slotAdapter);

        slotSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index1=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public void slotsubmit(View view)
    {
        relativeLayout = (RelativeLayout) findViewById(R.id.slotRelative);

        if(index1!=0)
        {
            Cursor res=db.getSlot(slots[index1]);
            if(res.getCount()!=0) {
                while (res.moveToNext()) {
                    time = res.getString(0);
                    count = res.getInt(1);
                }
            }

            if(count!=0)
            {
                count=count-1;
                boolean up=db.updateSlot(slots[index1],count);
                if(up) {
                    boolean insert = db.insertSlot(id, slots[index1]);

                    if (insert == true) {
                        Toast.makeText(getApplicationContext(), "Booked Successfully", Toast.LENGTH_LONG).show();

                        new Thread(new Runnable() {

                            public void run() {

                                try {

                                    GmailSender sender = new GmailSender(

                                            "ptksinstitute@gmail.com",

                                            "Ptks@12345");


                                    //sender.addAttachment(Environment.DIRECTORY_PICTURES.concat(path));

                                    //Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_LONG).show();


                                    sender.sendMail("Confrimation of Slot Booking", "Slot Booked. \nTime : " + slots[index1],

                                            "ssaipriyanka@gmail.com",

                                            email);

                                    // Toast.makeText(getApplicationContext(),"Mail is sent",Toast.LENGTH_LONG).show();

                                } catch (Exception e) {

                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                                }

                            }

                        }).start();

                        Intent intent = new Intent(SlotActivity.this, MainMenuActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Booking Failed!!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Slot is not available for the day!!!!", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            TextView error=(TextView) findViewById(R.id.txtVwSlotError);
            error.setError("Please select one slot");
        }
    }
}
