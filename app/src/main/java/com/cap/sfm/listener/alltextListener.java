package com.cap.sfm.listener;

import com.cap.sfm.Models.Text;

public interface alltextListener {
    void onAllTextClicked(Text text);
    void onLongTextClicked(Text text,int pos);
}
