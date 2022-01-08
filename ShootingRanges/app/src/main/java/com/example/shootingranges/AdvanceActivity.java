package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdvanceActivity extends AppCompatActivity {

    public String id,name,email,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        setTitle("Advance Course Screen");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        Intent intent=getIntent();
        id=intent.getStringExtra("Id");
        name=intent.getStringExtra("Name");
        email=intent.getStringExtra("Email");
        phone=intent.getStringExtra("Phone");

        Button apply=(Button)findViewById(R.id.btnApplyAdvance);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdvanceActivity.this,UploadActivity.class);
                intent.putExtra("Id", id);
                intent.putExtra("Name", name);
                intent.putExtra("Email",email);
                intent.putExtra("Phone", phone);
                startActivity(intent);
            }
        });
    }
}
