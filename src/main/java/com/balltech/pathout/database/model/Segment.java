package com.balltech.pathout.database.model;

import lombok.Getter;
import lombok.Setter;

public class Segment {
    private static final String SPLIT_SYMBOL = ",";
    @Getter
    @Setter
    private int mStart;
    @Getter
    @Setter
    private int mEnd;

    public Segment(int start, int end) {
        mStart = start;
        mEnd = end;
    }

    public Segment(String json) {
        String[] split = json.split(SPLIT_SYMBOL);
        if (split == null || split.length != 2) {
            return;
        }
        mStart = Integer.valueOf(split[0]);
        mEnd = Integer.valueOf(split[1]);
    }

    public String getJsonStr() {
        return mStart + SPLIT_SYMBOL + mEnd;
    }
}
