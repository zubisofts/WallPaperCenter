package com.zubisofts.solutions.wallpapercenter.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.adapters.WallPaperGalleryAdapter;
import com.zubisofts.solutions.wallpapercenter.model.WallPaper;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MainActivity extends AppCompatActivity implements WallPaperGalleryAdapter.WallPaperItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        WallPaperGalleryAdapter adapter=new WallPaperGalleryAdapter();
        adapter.setWallPapers(fetchWallPapers());
        adapter.setWallPaperItemClickListener(this);

        ScaleInAnimationAdapter animationAdapter=new ScaleInAnimationAdapter(adapter);
        animationAdapter.setDuration(300);
        animationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(animationAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onWallPaperSelected(int position, ImageView imageView) {
        Intent intent=new Intent(this, WallPaperPreviewActivity.class);
        intent.putExtra("index",position);

        ArrayList<Integer> wallpapers=new ArrayList<>();
        ArrayList<WallPaper> list = fetchWallPapers();
        for(WallPaper wallPaper:list){
            wallpapers.add(wallPaper.getImageResource());
        }
        intent.putExtra("wallpapers",wallpapers);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.putExtra("image_transition_name",imageView.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(MainActivity.this,
                            (View)imageView,
                            imageView.getTransitionName());

            startActivity(intent,options.toBundle());
        }else{
            startActivity(intent);
        }

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
        WallPaper w9=new WallPaper("C1",R.drawable.c1);
        WallPaper w10=new WallPaper("C2",R.drawable.c2);
        WallPaper w11=new WallPaper("C3",R.drawable.c3);
        WallPaper w12=new WallPaper("C4",R.drawable.c4);
        WallPaper w13=new WallPaper("C5",R.drawable.c5);
        WallPaper w14=new WallPaper("C6",R.drawable.c6);
        WallPaper w15=new WallPaper("C7",R.drawable.c7);
        WallPaper w16=new WallPaper("C8",R.drawable.c8);
        WallPaper w17=new WallPaper("C9",R.drawable.c9);
        WallPaper w18=new WallPaper("C10",R.drawable.c10);
        wallPapers.add(w1);
        wallPapers.add(w2);
        wallPapers.add(w3);
        wallPapers.add(w4);
        wallPapers.add(w5);
        wallPapers.add(w6);
        wallPapers.add(w7);
        wallPapers.add(w9);
        wallPapers.add(w10);
        wallPapers.add(w11);
        wallPapers.add(w12);
        wallPapers.add(w13);
        wallPapers.add(w14);
        wallPapers.add(w15);
        wallPapers.add(w16);
        wallPapers.add(w17);
        wallPapers.add(w18);

        return wallPapers;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_share_app){
            Toast.makeText(this, "dggjhkl;", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}