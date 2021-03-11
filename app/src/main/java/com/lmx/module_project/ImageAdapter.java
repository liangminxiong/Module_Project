package com.lmx.module_project;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lmx.common.utils.ImageUtils;
import com.lmx.common.widget.NineGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmx on 2021/3/3
 * Describe:
 */
public class ImageAdapter implements NineGridView.NineGridAdapter<String> {

    private List<String> images = new ArrayList<>();
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images != null ? images.size() : 0;
    }

    @Override
    public String getItem(int position) {
        return images.get(position);
    }

    @Override
    public View getView(int position, View itemView) {
        ImageView imageView = new ImageView(context);
        ImageUtils.setImageUrl(imageView, getItem(position), 0);
        return imageView;
    }
}
