package com.zubisofts.solutions.wallpapercenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WallPaperGalleryAdapter.WallPaperItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        WallPaperGalleryAdapter adapter=new WallPaperGalleryAdapter();
        adapter.setWallPapers(fetchWallPapers());
        adapter.setWallPaperItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWallPaperSelected(WallPaper wallPaper) {

        Glide.with(this)
                .asBitmap()
                .load(wallPaper.getImageResource())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        WallpaperManager manager=WallpaperManager.getInstance(MainActivity.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            try {
                                WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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

                                    wallpaperManager.setBitmap(bitmap, new Rect(start.x, start.y, end.x, end.y), false);
                                } else {
                                    wallpaperManager.setBitmap(bitmap);
                                }                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }

    private ArrayList<WallPaper> fetchWallPapers(){
        ArrayList<WallPaper> wallPapers=new ArrayList<>();
        WallPaper w1=new WallPaper("Building Overhead",R.drawable.building_overhead);
        WallPaper w2=new WallPaper("City Building",R.drawable.city_building);
        WallPaper w3=new WallPaper("Colourful Paint Splatter",R.drawable.colorful_paint_splatter);
        WallPaper w4=new WallPaper("Frozen Flakes",R.drawable.frozen_flakes);
        WallPaper w5=new WallPaper("Joker",R.drawable.joker);
        WallPaper w6=new WallPaper("Love Path",R.drawable.love_path);
        WallPaper w7=new WallPaper("Pink Blue Flower",R.drawable.pink_blue_flowers);
        WallPaper w8=new WallPaper("The Verge",R.drawable.the_verge);

        wallPapers.add(w1);
        wallPapers.add(w2);
        wallPapers.add(w3);
        wallPapers.add(w4);
        wallPapers.add(w5);
        wallPapers.add(w6);
        wallPapers.add(w7);
        wallPapers.add(w8);

        return wallPapers;
    }
}