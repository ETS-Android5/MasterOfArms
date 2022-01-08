package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

public class MatchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        setTitle("Matches Information");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
    }

    public void pdfView(View view)
    {
        Intent intent=new Intent(MatchesActivity.this, PDFViewActivity.class);
        startActivity(intent);
    }

    public void MatchesOk(View view)
    {
        Intent intent=new Intent(MatchesActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }
}
