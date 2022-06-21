package com.yourcodereview.iosif.task_2;

public class ResourceAdapter {
    static public ShortLinkResponse createShortLinkResponse(Resource resource){
        return new ShortLinkResponse(resource.getLink());
    }
    static public StatisticsResponse createStatisticsResponse(Resource resource, long rank){
        return new StatisticsResponse(resource.getLink(), resource.getOriginal(), resource.getCount(), rank);
    }
}
