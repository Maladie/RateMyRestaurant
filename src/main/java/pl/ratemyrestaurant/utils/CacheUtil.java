package pl.ratemyrestaurant.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.ratemyrestaurant.config.Constants;
import pl.ratemyrestaurant.service.impl.AsyncTokenExpire;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CacheUtil {

    private static final int TIMEOUT_IN_SECONDS =600;// Constants.CACHE_TIMEOUT_IN_SEC;
    private static Logger logger = LogManager.getLogger(CacheUtil.class);
    private static ConcurrentMap<String, Object> concurrentMap;
    private static Cache<String, Object> cache;

    private CacheUtil() {
    }

    public static void init() {
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .removalListener((RemovalNotification<String, Object> notification) -> {
                    if (notification.getCause() == RemovalCause.EXPIRED) {
                        logger.debug("Data expired: " + notification.getKey());
                    } else {
                        logger.debug("Data didn't expired but removed intentionally. Token: " + notification.getKey());
                    }
                    //TODO

                    try {
                        AsyncTokenExpire.expireToken(notification.getKey());
                        logger.debug("Data removed from cache " + notification.getKey());
                    } catch (Exception e) {
                        logger.debug("An error occurred while removing. Data.key: " + notification.getKey());
                        logger.catching(e);
                    }
                }).build();
        concurrentMap = cache.asMap();
    }

    public static void addToCache(String tag, Object value) {
        logger.debug("Inserting to cache " + tag);
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
