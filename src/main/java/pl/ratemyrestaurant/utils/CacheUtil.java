package pl.ratemyrestaurant.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalNotification;
import pl.ratemyrestaurant.config.Constants;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CacheUtil {

    public static final int TIMEOUT_IN_SECONDS = Constants.CACHE_TIMEOUT_IN_SEC;

    private static ConcurrentMap<String, Object> concurrentMap;
    private static Cache<String, Object> cache;

    private CacheUtil() {
    }

    public static void init() {
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .removalListener((RemovalNotification<String, Object> notification) -> {
                    if (notification.getCause() == RemovalCause.EXPIRED) {
                        System.out.println("Data expired:" + notification.getKey());
                    } else {
                        System.out.println("Data didn't expired but removed intentionaly. Token: " + notification.getKey());
                    }
                    //TODO

                    try {
                        //TODO more tests... - make calls async or sth
                        CacheUtil.removeFromCache(notification.getKey());
                    } catch (Exception e) {
                        System.out.println("An error occured while sending token to expire queue. Token: " + notification.getKey());
                        e.printStackTrace();
                    }
                }).build();
        concurrentMap = cache.asMap();
    }

    public static void addToCache(String tag, Object value) {
        concurrentMap.put(tag, value);
    }


    public static Object getFromCache(String tag) {
        if(concurrentMap != null) {
            return concurrentMap.get(tag);
        } else {
            return null;
        }
    }
    public static void removeFromCache(String tag) {
        if (concurrentMap != null)
            concurrentMap.remove(tag);
    }

    public static void cleanCache() {
        if (cache != null)
            cache.cleanUp();
    }

    public static void invalidateAllCache() {
        if (cache != null)
            cache.invalidateAll();
    }
}
