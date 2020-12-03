package com.zubisofts.solutions.wallpapercenter;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WallPaperDestinationFragment extends BottomSheetDialogFragment {

    WallPaper wallPaper;

    public WallPaperDestinationFragment(WallPaper wallPaper) {
        this.wallPaper = wallPaper;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wall_paper_destination_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] titles = new String[]{"Set As Home-Screen Only", "Set As Lock-Screen Only", "Set As Home-Screen And Lock-Screen"};
        int[] icons = new int[]{R.drawable.ic_home, R.drawable.ic_lock_screen, R.drawable.ic_add_home};
        ItemAdapter adapter = new ItemAdapter(titles, icons);
        adapter.setListener(new OptionListener() {
            @Override
            public void onOptionSelected(int position) {
                setWallpaper(position);
                dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setWallpaper(final int flagSystem) {

        Glide.with(this)
                .asBitmap()
                .load(wallPaper.getImageResource())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            try {
                                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());

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
                                    wallpaperManager.setBitmap(bitmap, null, true);
                                } else if (flagSystem == 1) {
                                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                                } else {
                                    wallpaperManager.setBitmap(bitmap, new Rect(start.x, start.y, end.x, end.y), false);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;
        final ImageView icon;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_wall_paper_destination_list_dialog_item, parent, false));
            text = itemView.findViewById(R.id.text);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int[] icons;
        private String[] titles;

        private OptionListener listener;

        public ItemAdapter(String[] titles, int[] icons) {
            this.titles = titles;
            this.icons = icons;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.text.setText(titles[position]);
            holder.icon.setImageResource(icons[position]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onOptionSelected(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        public void setListener(OptionListener listener) {
            this.listener = listener;
        }
    }

    public interface OptionListener {
        public void onOptionSelected(int position);
    }

}