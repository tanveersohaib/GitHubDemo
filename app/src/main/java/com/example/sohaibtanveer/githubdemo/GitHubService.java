package com.example.sohaibtanveer.githubdemo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GitHubService {

    @GET("/search/repositories?")
    Call<SearchRepositoryResult> getRepositorySearchResults(@Query("q") String repository,@Query("access_token") String accessToken);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    Call<AccessToken> getAccessToken(@Field("client_id") String clientId,@Field("client_secret") String clientSecret,
                                     @Field("code") String code);

    @GET("/user?")
    Call<User> getUser(@Query("access_token") String token);

    @GET
    Call<ReadmeObject> getReadmeObject(@Url String url,@Query("access_token") String accessToken);

    @GET
    Call<List<Directory>> getFiles(@Url String url,@Query("ref") String ref,@Query("access_token") String accessToken);

    @GET
    Call<List<RepoBranchPOJO>> getBranches(@Url String url,@Query("access_token") String access_token);

    @GET
    Call<List<TagsPOJO>> getTags(@Url String url,@Query("access_token") String access_token);

}
