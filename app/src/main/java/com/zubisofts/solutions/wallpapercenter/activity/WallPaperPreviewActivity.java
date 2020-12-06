package com.zubisofts.solutions.wallpapercenter.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.adapters.WallPaperPagerAdapter;
import com.zubisofts.solutions.wallpapercenter.fragments.WallPaperDestinationFragment;
import com.zubisofts.solutions.wallpapercenter.model.WallPaper;

import java.io.IOException;
import java.util.ArrayList;

public class WallPaperPreviewActivity extends AppCompatActivity implements WallPaperDestinationFragment.OptionListener {

    private int index;
    private ArrayList<Integer> wallPapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_preview);
//        makeStatusBarTransparent();

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

        findViewById(R.id.btnOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wallPapers != null) {
                    new WallPaperDestinationFragment(WallPaperPreviewActivity.this).show(getSupportFragmentManager(), "Wallpaper");
                }
            }
        });

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

    @Override
    public void onOptionSelected(int position) {
        new WallPaperAsyncTask().execute(position);
    }

    private int setWallpaper(final int flagSystem) {

        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, wallPapers.get(index));
        Bitmap bitmap = drawable.getBitmap();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallPaperPreviewActivity.this);

                int wallpaperHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
                int wallpaperWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

                Point start = new Point(0, 0);
                Point end = new Point(bitmap.getWidth(), bitmap.getHeight());

                if (bitmap.getWidth() > wallpaperWidth) {
                    start.x = (bitmap.getWidth() - wallpaperWidth) / 2;
                    end.x = start.x + wallpaperWidth;
                }

                if (bitmap.getHeight() > wallpaperHeight) {
                    start.y = (bitmap.getHeight() - wallpaperHeight) / 2;
                    end.y = start.y + wallpaperHeight;
                }

                if (flagSystem == 0) {
                    return wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                } else if (flagSystem == 1) {
                    return wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                } else {
                    return wallpaperManager.setBitmap(bitmap, new Rect(start.x, start.y, end.x, end.y), false);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

}