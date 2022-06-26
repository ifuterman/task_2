package com.yourcodereview.iosif.task_2.protocol;

public class ShortLinkRequest {
    private String original;

    public ShortLinkRequest() {
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public ShortLinkRequest(String original) {
        this.original = original;
    }
}
