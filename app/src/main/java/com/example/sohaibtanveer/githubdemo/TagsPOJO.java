package com.example.sohaibtanveer.githubdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagsPOJO {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("zipball_url")
    @Expose
    private String zipballUrl;
    @SerializedName("tarball_url")
    @Expose
    private String tarballUrl;
    @SerializedName("commit")
    @Expose
    private CommitPOJO commit;
    @SerializedName("node_id")
    @Expose
    private String nodeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipballUrl() {
        return zipballUrl;
    }

    public void setZipballUrl(String zipballUrl) {
        this.zipballUrl = zipballUrl;
    }

    public String getTarballUrl() {
        return tarballUrl;
    }

    public void setTarballUrl(String tarballUrl) {
        this.tarballUrl = tarballUrl;
    }

    public CommitPOJO getCommit() {
        return commit;
    }

    public void setCommit(CommitPOJO commit) {
        this.commit = commit;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

}
