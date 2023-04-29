package com.balltech.pathout.database.model;

public class Segment {
    private int mStart;

    private int mEnd;

    public void setSegment(int start, int end) {
        mStart = start;
        mEnd = end;
    }

    public int getStart() {
        return mStart;
    }

    public int getEnd() {
        return mEnd;
    }
}
