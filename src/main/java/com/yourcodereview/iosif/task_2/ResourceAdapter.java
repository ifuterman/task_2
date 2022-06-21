package com.yourcodereview.iosif.task_2;

public class ResourceAdapter {
    static public ShortLinkResponse createShortLinkResponse(Resource resource){
        return new ShortLinkResponse(resource.getLink());
    }
}
