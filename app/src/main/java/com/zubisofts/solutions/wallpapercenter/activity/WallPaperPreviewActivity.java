package com.zubisofts.solutions.wallpapercenter.activity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionMenu;
import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.adapters.WallPaperPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class WallPaperPreviewActivity extends AppCompatActivity implements  View.OnClickListener {

    private int index;
    private ArrayList<Integer> wallPapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_preview);
        makeStatusBarTransparent();

        index = getIntent().getIntExtra("index", 0);
        String imageTransitionName = getIntent().getStringExtra("image_transition_name");
        wallPapers = getIntent().getIntegerArrayListExtra("wallpapers");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setTransitionName(imageTransitionName);
        WallPaperPagerAdapter adapter = new WallPaperPagerAdapter(wallPapers);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.menu_item_home_screen).setOnClickListener(this);
        findViewById(R.id.menu_item_lock_screen).setOnClickListener(this);
        findViewById(R.id.menu_item_both).setOnClickListener(this);
        ((FloatingActionMenu)findViewById(R.id.fab_menu)).setClosedOnTouchOutside(true);

    }

    private void makeStatusBarTransparent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window=getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
            window.setStatusBarColor(Color.TRANSPARENT);
            }

    }

    @Override
    public void onClick(View view) {
        int flag;
        if(view.getId()==R.id.menu_item_home_screen){
            flag=0;
        }else if(view.getId()==R.id.menu_item_lock_screen){
            flag=1;
        }else{
            flag = 2;
        }

        if (wallPapers != null) {
            new WallPaperAsyncTask().execute(flag);
            ((FloatingActionMenu)findViewById(R.id.fab_menu)).close(true);
        }
    }

    private class WallPaperAsyncTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... index) {

            final int i = setWallpaper(index[0]);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (i > 0) {
                        Toast.makeText(getApplicationContext(), "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return null;
        }
    }

    private int setWallpaper(final int flagSystem) {

        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, wallPapers.get(index));
        Bitmap bitmap = drawable.getBitmap();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallPaperPreviewActivity.this);

                if (flagSystem == 0) {
                    return wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                } else if (flagSystem == 1) {
                    return wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                } else {
                    return wallpaperManager.setBitmap(bitmap, null, false);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        if(((FloatingActionMenu)findViewById(R.id.fab_menu)).isOpened()){
            ((FloatingActionMenu)findViewById(R.id.fab_menu)).close(true);
        }else {
            super.onBackPressed();
        }
    }
}