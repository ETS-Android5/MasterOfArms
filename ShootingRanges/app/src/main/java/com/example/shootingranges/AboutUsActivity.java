package com.example.shootingranges;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutUsActivity extends AppCompatActivity {

    private ViewPager slider;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("About Us");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        slider = findViewById(R.id.slider);
        sliderAdapter = new SliderAdapter(this);
        slider.setAdapter(sliderAdapter);

    }

    private class SliderAdapter extends PagerAdapter{

        private Context context;
        private LayoutInflater inflater;

        public SliderAdapter(Context context) {
            this.context = context;
        }

        int[] list_img = {
                R.drawable.i1,
                R.drawable.i6,
                R.drawable.i3,
                R.drawable.i4,
                R.drawable.i5,
                R.drawable.i6};

        @Override
        public int getCount() {
            return list_img.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
           inflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
           View view=inflater.inflate(R.layout.item_slider,container,false);
           ImageView imageView=view.findViewById(R.id.img_item);
           imageView.setImageResource(list_img[position]);
           imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent=new Intent(context,FullScreenActivity.class);
                   intent.putExtra("pos",position);
                   startActivity(intent);
               }
           });
           container.addView(view);
           return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }

    public void call(View view)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:09930947174"));

        if (ActivityCompat.checkSelfPermission(AboutUsActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void abtOk(View view)
    {
        Intent intent=new Intent(AboutUsActivity.this,MainMenuActivity.class);
        startActivity(intent);
    }
}

