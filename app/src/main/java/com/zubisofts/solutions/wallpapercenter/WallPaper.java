package com.zubisofts.solutions.wallpapercenter;

import java.io.Serializable;

public class WallPaper implements Serializable {

    public WallPaper(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    private String name;
    private int imageResource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
