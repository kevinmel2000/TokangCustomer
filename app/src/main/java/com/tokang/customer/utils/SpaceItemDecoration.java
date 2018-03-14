package com.tokang.customer.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by royli on 2/25/2018.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 0;
        outRect.right = space;
        outRect.bottom = 0;

        if(parent.getChildLayoutPosition(view) == 0){
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
