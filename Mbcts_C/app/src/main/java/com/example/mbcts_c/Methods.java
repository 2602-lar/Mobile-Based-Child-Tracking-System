package com.example.mbcts_c;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Methods {

    @FormUrlEncoded
    @POST("user-operations/verify-child/")
    Call<ChildSetup> VerifyChild(@Field("child_id")String child_id, @Field("guardian_id")String guardian_id);

    @FormUrlEncoded
    @POST("user-operations/verify-child/")
    Call<ChildSetup> Panic(@Field("child_id")String child_id, @Field("location")String location);

    @FormUrlEncoded
    @POST("auxillary/home-tip/")
    Call<ChildSetup> HomeTip(@Field("child_id")String child_id);


}
