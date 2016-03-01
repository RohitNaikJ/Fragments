package com.rohit.fragmentstutorial;

/**
 * Created by Rohit on 2/29/16.
 */
public class CourseEntry {
    private String code;
    private String name;
    private String descript;
    private int credits;
    private String ltp;
    private int id;

    public CourseEntry(String code, String name, String descript, int credits, String ltp, int id) {
        this.code = code;
        this.name = name;
        this.descript = descript;
        this.credits = credits;
        this.ltp = ltp;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescript() {
        return descript;
    }

    public int getCredits() {
        return credits;
    }

    public String getLtp() {
        return ltp;
    }

    public int getId(){
        return id;
    }
}
