package com.rohit.fragmentstutorial;

/**
 * Created by Rohit on 3/28/16.
 */
public class CommentEntry {
    private String user;
    private String body;
    private String treadable;

    public CommentEntry(String user, String body, String treadable) {
        this.user = user;
        this.body = body;
        this.treadable = treadable;
    }

    public String getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public String getTreadable() {
        return treadable;
    }
}
