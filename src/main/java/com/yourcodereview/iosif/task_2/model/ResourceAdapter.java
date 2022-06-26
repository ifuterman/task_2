package com.yourcodereview.iosif.task_2.model;

import com.yourcodereview.iosif.task_2.protocol.ShortLinkResponse;
import com.yourcodereview.iosif.task_2.protocol.StatisticsResponse;

public class ResourceAdapter {
    static public ShortLinkResponse createShortLinkResponse(Resource resource){
        return new ShortLinkResponse(resource.getLink());
    }
    static public StatisticsResponse createStatisticsResponse(Resource resource, long rank){
        return new StatisticsResponse(resource.getLink(), resource.getOriginal(), resource.getCount(), rank);
    }
}
