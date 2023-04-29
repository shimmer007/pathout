package com.balltech.pathout.highlight;

import com.balltech.pathout.User.User;
import com.balltech.pathout.database.model.Segment;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HighLight {
    private static final Logger LOG = Logger.getLogger(HighLight.class);
    public static final String TABLE_NAME = "highlight";
    public static final String USER_ID = "user_id";
    public static final String COVER_PATH = "cover_path";
    public static final String VIDEO_SEG = "video_seg";
    public static final String ORIGIN_VIDEO_PATH = "orgin_video_path";
    public static final String TIMESTAMP = "timestamp";
    @Getter
    @Setter
    private String mUid;
    @Getter
    @Setter
    private String mCoverPath;
    @Getter
    @Setter
    private Segment mSegment;
    @Getter
    @Setter
    private int mTimeStamp;
    @Getter
    @Setter
    private String mOriginVideoPath;
    private String mVideoPath;

    public HighLight(ResultSet resultSet) {
        try {
            mUid = resultSet.getString(USER_ID);
            mCoverPath = resultSet.getString(COVER_PATH);
            mSegment = new Segment(resultSet.getString(VIDEO_SEG));
            mOriginVideoPath = resultSet.getString(ORIGIN_VIDEO_PATH);
            mTimeStamp = resultSet.getInt(TIMESTAMP);
        } catch (SQLException e) {
            LOG.error("Error init highlight:" + e.toString());
        }
    }
}
