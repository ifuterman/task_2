package com.yourcodereview.iosif.task_2;

public class StatisticsResponse {
    private String link;
    private String original;
    private  Long rank;
    private Long count;

    public StatisticsResponse(String link, String original, Long count, Long rank) {
        this.link = link;
        this.original = original;
        this.count = count;
        this.rank = rank;
    }

    public String getLink() {
        return link;
    }

    public String getOriginal() {
        return original;
    }

    public Long getRank() {
        return rank;
    }

    public Long getCount() {
        return count;
    }

    public StatisticsResponse() {
    }
}
