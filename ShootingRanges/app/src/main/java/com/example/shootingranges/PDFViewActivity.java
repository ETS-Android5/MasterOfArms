package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PDFViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        setTitle("PDF");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));


        PDFView pdfView=findViewById(R.id.pdf_viewer);
        pdfView.fromAsset("7th West Zone Shooting Circular.pdf")
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}
