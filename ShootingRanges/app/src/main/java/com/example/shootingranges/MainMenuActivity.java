package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    String id,name,phone,email,slot;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle("Main Menu Screen");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db=new DatabaseHelper(this);

        Intent intent=getIntent();
        id=intent.getStringExtra("Id");
        name=intent.getStringExtra("Name");
        email=intent.getStringExtra("Email");
        phone=intent.getStringExtra("Phone");
    }

    public void pelletbooking(View view)
    {
        Intent intent=new Intent(MainMenuActivity.this,PelletActivity.class);
        intent.putExtra("Email",email);
        intent.putExtra("Phone",phone);
        startActivity(intent);
    }

    public void slotbooking(View view)
    {
        Cursor res=db.getPlayerSlot(id);
        if(res.getCount()!=0) {
            while (res.moveToNext()) {
                slot = res.getString(9);
            }
        }

        if(slot==null)
        {
            Intent intent=new Intent(MainMenuActivity.this,SlotActivity.class);
            intent.putExtra("Id", id);
            intent.putExtra("Name", name);
            intent.putExtra("Email",email);
            intent.putExtra("Phone", phone);
            startActivity(intent);
        }

        else
        {
            Intent intent=new Intent(MainMenuActivity.this,CancelSlotActivity.class);
            intent.putExtra("Id", id);
            intent.putExtra("Name", name);
            intent.putExtra("Email",email);
            intent.putExtra("Slot",slot);
            startActivity(intent);
        }

    }

    public void matches(View view)
    {
        Intent intent=new Intent(MainMenuActivity.this,MatchesActivity.class);
        startActivity(intent);
    }

    public void aboutUs(View view)
    {
        Intent intent=new Intent(MainMenuActivity.this,AboutUsActivity.class);
        startActivity(intent);
    }
}
