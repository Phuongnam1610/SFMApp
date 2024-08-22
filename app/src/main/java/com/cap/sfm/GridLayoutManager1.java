package com.cap.sfm;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

public class GridLayoutManager1 extends GridLayoutManager {
    public GridLayoutManager1(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
