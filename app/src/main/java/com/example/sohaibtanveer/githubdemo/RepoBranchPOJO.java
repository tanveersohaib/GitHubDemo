package com.example.sohaibtanveer.githubdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepoBranchPOJO {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("commit")
    @Expose
    private CommitPOJO commit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommitPOJO getCommit() {
        return commit;
    }

    public void setCommit(CommitPOJO commit) {
        this.commit = commit;
    }
}
