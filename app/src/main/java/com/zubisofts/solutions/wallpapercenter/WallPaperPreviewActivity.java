package com.zubisofts.solutions.wallpapercenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class WallPaperPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_preview);

        ImageView imageView=findViewById(R.id.image);

        final WallPaper wallPaper= (WallPaper) getIntent().getSerializableExtra("wallpaper");
        if (wallPaper != null) {
            Glide.with(this)
                    .load(wallPaper.getImageResource())
                    .into(imageView);
        }

        findViewById(R.id.btnOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WallPaperDestinationFragment(wallPaper).show(getSupportFragmentManager(),"Wallpaper");
            }
        });

    }
}