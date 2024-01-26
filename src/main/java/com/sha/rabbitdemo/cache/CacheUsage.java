package com.sha.rabbitdemo.cache;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CacheUsage {

    public static void main(String[] args) {
        Cache<String, DataObject> caffeineCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        DataObject dataObject = caffeineCache.getIfPresent("data");
        if (dataObject == null) {
            dataObject = caffeineCache.get("data", k -> DataObject.get("data"));
            System.out.println(caffeineCache.getIfPresent("data").getData());
        }
        System.out.println(caffeineCache.getIfPresent("data").getData());
        caffeineCache.invalidateAll();
        if (caffeineCache.getIfPresent("data") != null) {
            System.out.println(caffeineCache.getIfPresent("data").getData());
        } else {
            System.out.println("Cache is empty");
        }

        AsyncLoadingCache<String, DataObject> caffeineCacheAsync = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(10)
                .buildAsync(k -> DataObject.get("Data for : " + k));

        caffeineCacheAsync.get( "key").thenAccept(data -> System.out.println(data.getData()));

        caffeineCacheAsync.get("key1").thenAccept(data -> System.out.println(data.getData()));
    }
    // build an sync cache without looking to the documentation
    // build an async cache without looking to the documentation

}
