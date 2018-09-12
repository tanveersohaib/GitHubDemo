package com.example.sohaibtanveer.githubdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommitPOJO {
    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("url")
    @Expose
    private String url;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
