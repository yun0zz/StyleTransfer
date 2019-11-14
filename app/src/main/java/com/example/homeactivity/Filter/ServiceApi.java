package com.example.homeactivity.Filter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceApi {

    /** 필터 */
    @GET("preview/{style_id}/abc")
    Call<ImageResult> ResultImage(@Path("style_id") int style_id);
}
