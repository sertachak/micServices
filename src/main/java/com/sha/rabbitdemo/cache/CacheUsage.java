package com.sha.rabbitdemo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

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
    }
}
