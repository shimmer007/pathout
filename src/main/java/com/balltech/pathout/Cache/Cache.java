package com.balltech.pathout.Cache;

import com.balltech.pathout.User.User;
import com.balltech.pathout.database.utils.DbUtil;
import com.balltech.pathout.highlight.HighLight;
import org.apache.log4j.Logger;

import java.util.*;

public class Cache {
    private static final Logger LOG = Logger.getLogger(Cache.class);
    private static Cache sInstance;
    private Set<User> mUserCache;
    private Map<String, List<HighLight>> mHighlightCache = new HashMap<>();

    private Cache() {
        // TODO:现在的实现是第一次加载全部的数据库内容到cache里, 但是随着数据库增大会导致首次加载很慢. 后续考虑通过增量的方式进行优化
        mUserCache = DbUtil.getAllUser();
        if (mUserCache != null) {
            for (User tmp : mUserCache) {
                mHighlightCache.put(tmp.getMUid(), DbUtil.getHighLights(tmp));
            }
        }
    }

    public Cache getInstance() {
        if (sInstance == null) {
            synchronized (Cache.class) {
                if (sInstance == null) {
                    sInstance = new Cache();
                }
            }
        }
        return sInstance;
    }

    public List<HighLight> getHilightList(User user) {
        if (user == null) {
            LOG.error("Invalid user info when get high light.");
            return new ArrayList<>();
        }

        List<HighLight> cacheList = mHighlightCache.get(user.getMUid());
        return cacheList == null ? new ArrayList<>() : cacheList;
    }
}
