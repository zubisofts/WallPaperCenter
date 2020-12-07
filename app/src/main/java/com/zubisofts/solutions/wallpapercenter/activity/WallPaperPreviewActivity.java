package com.zubisofts.solutions.wallpapercenter.activity;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionMenu;
import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.adapters.WallPaperPagerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class WallPaperPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int WRITE_PERMISSION_CODE = 1002;
    private int index;
    private ArrayList<Integer> wallPapers;
    boolean isSave;

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
        findViewById(R.id.menu_save_wallpaper).setOnClickListener(this);
        findViewById(R.id.menu_share_wallpaper).setOnClickListener(this);
        ((FloatingActionMenu) findViewById(R.id.fab_menu)).setClosedOnTouchOutside(true);

    }

    private void makeStatusBarTransparent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
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

        if (view.getId() == R.id.menu_item_home_screen) {
            if (wallPapers != null) {
                new WallPaperAsyncTask().execute(0);
            }
        } else if (view.getId() == R.id.menu_item_lock_screen) {
            if (wallPapers != null) {
                new WallPaperAsyncTask().execute(1);
            }
        } else if (view.getId() == R.id.menu_item_both) {
            if (wallPapers != null) {
                new WallPaperAsyncTask().execute(2);
            }
        } else if (view.getId() == R.id.menu_share_wallpaper) {
            shareWallpaper(wallPapers.get(index));
        } else if (view.getId() == R.id.menu_save_wallpaper) {
            new SaveWallPaperAsyncTask().execute(wallPapers.get(index));
        }

        ((FloatingActionMenu) findViewById(R.id.fab_menu)).close(true);


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

    private void shareWallpaper(int resId) {

        isSave = false;

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, resId);
            Bitmap bitmap = drawable.getBitmap();


            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Description", null);
            Uri uri = Uri.parse(path);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Share Image"));
        } else {
            requestPermission();
        }

    }

    private boolean saveWallpaper(Integer imageRes) {

        isSave = true;

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            File filesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(this, imageRes);
            Bitmap bitmap = drawable.getBitmap();

            File f = new File(filesDir, "WC");

            if (!f.exists()) {
                f.mkdir();
            }
            File outFile = new File(f, "wc_" + new Date().getTime() + ".png");
            try {
                return bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(outFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            requestPermission();
        }
        return false;
    }

    private class SaveWallPaperAsyncTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            final boolean saved = saveWallpaper(params[0]);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (saved) {
                        Toast.makeText(getApplicationContext(), "Wallpaper saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error saving wallpaper", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return null;
        }
    }

    private void requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_CODE);
        }

    }

    @Override
    public void onBackPressed() {
        if (((FloatingActionMenu) findViewById(R.id.fab_menu)).isOpened()) {
            ((FloatingActionMenu) findViewById(R.id.fab_menu)).close(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isSave) {
                new SaveWallPaperAsyncTask().execute(wallPapers.get(index));
            } else {
                shareWallpaper(wallPapers.get(index));
            }
        }
    }
}