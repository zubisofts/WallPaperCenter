package com.zubisofts.solutions.wallpapercenter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.model.WallPaper;

import java.util.ArrayList;

public class WallPaperPagerAdapter extends PagerAdapter {

    private ArrayList<Integer> wallpapers;

    public WallPaperPagerAdapter(ArrayList<Integer> wallpapers) {
        this.wallpapers = wallpapers;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        FrameLayout frameLayout= (FrameLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.wallpaper_preview_item,container,false);
        ImageView imageView=frameLayout.findViewById(R.id.image);
//        imageView.setTransitionName("position_"+position);
        imageView.setImageResource(wallpapers.get(position));

        container.addView(frameLayout);

        return frameLayout;
    }

    @Override
    public int getCount() {
        return wallpapers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
