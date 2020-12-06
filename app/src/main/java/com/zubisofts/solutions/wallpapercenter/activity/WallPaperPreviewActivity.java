package com.zubisofts.solutions.wallpapercenter.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.adapters.WallPaperPagerAdapter;
import com.zubisofts.solutions.wallpapercenter.fragments.WallPaperDestinationFragment;
import com.zubisofts.solutions.wallpapercenter.model.WallPaper;

import java.util.ArrayList;

public class WallPaperPreviewActivity extends AppCompatActivity {

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_preview);
//        makeStatusBarTransparent();

        index= getIntent().getIntExtra("index",0);
        String imageTransitionName=getIntent().getStringExtra("image_transition_name");
        final ArrayList<Integer> wallPapers= getIntent().getIntegerArrayListExtra("wallpapers");

        ViewPager viewPager=findViewById(R.id.viewpager);
        viewPager.setTransitionName(imageTransitionName);
        WallPaperPagerAdapter adapter=new WallPaperPagerAdapter(wallPapers);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.btnOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wallPapers != null) {
                    new WallPaperDestinationFragment(wallPapers.get(index)).show(getSupportFragmentManager(),"Wallpaper");
                }
            }
        });

    }

    private void makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window=getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
                window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}