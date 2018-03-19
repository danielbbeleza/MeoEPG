package com.example.meoepg.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by danielbeleza on 16/03/2018.
 */

public class Show {

    @SerializedName("CallLetter")
    @Expose
    private String callLetter;

    @SerializedName("Title")
    @Expose
    private String title;

    public Show(String callLetter, String title) {
        this.callLetter = callLetter;
        this.title = title;
    }

    public String getCallLetter() {
        return callLetter;
    }

    public void setCallLetter(String callLetter) {
        this.callLetter = callLetter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
