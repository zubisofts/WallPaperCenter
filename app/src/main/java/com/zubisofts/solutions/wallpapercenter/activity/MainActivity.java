package com.zubisofts.solutions.wallpapercenter.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.adapters.WallPaperGalleryAdapter;
import com.zubisofts.solutions.wallpapercenter.model.WallPaper;
import com.zubisofts.solutions.wallpapercenter.utils.CustomGridRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WallPaperGalleryAdapter.WallPaperItemClickListener {

    private CustomGridRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        WallPaperGalleryAdapter adapter=new WallPaperGalleryAdapter();
        adapter.setWallPapers(fetchWallPapers());
        adapter.setWallPaperItemClickListener(this);
        recyclerView.setAdapter(adapter);
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
        intent.putExtra("image_transition_name",imageView.getTransitionName());

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(MainActivity.this,
                        (View)imageView,
                        imageView.getTransitionName());

        startActivity(intent,options.toBundle());
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

    private void runAnimationAgain() {

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_down_to_up);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }
}