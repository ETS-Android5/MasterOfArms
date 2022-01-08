package com.example.shootingranges;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;

public class FullScreenActivity extends AppCompatActivity {

    private ViewPager fullSlider;
    private FullSliderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        setTitle("PTKS Pictures");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));


        fullSlider = findViewById(R.id.full_slider);
        adapter = new FullSliderAdapter(this);
        fullSlider.setAdapter(adapter);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);

        fullSlider.setCurrentItem(pos);
    }

    private class FullSliderAdapter extends PagerAdapter{
        private Context context;
        private LayoutInflater inflater;


        public FullSliderAdapter(Context context) {
            this.context = context;
        }

        int[] list_img = {
                R.drawable.i1,
                R.drawable.i2,
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
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_full, container, false);

            PhotoView photoView = view.findViewById(R.id.full_img);
            photoView.setImageResource(list_img[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((PhotoView) object);
        }
    }
}
