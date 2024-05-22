package com.example.mbcts;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Methods {

    @FormUrlEncoded
    @POST("token/")
    Call<Model> getUserData(@Field("username")String username,@Field("password") String password);

    @FormUrlEncoded
    @POST("user-operations/guardians/")
    Call<Guardian> SubmitGuardian(@Field("name")String name,@Field("surname") String surname,
                                  @Field("address") String address, @Field("number_of_children") Integer number_of_children,
                                  @Field("phonenumber") String phonenumber, @Field("phonenumber_2") String phonenumber_2,
                                  @Field("relationship") String relationship, @Field("guardian_id") String guardian_id
                                  );

    @FormUrlEncoded
    @POST("user-operations/children/")
    Call<Child> SubmitChild(
            @Field("firstname") String firstname, @Field("surname") String surname,
            @Field("date_of_birth")String date_of_birth, @Field("age") Integer age,
            @Field("number_of_guardians") Integer number_of_guardians, @Field("child_id") String child_id,
            @Field("guardian_id") String guardian_id
            );

    @FormUrlEncoded
    @POST("user-operations/create-application-user/")
    Call<User> SubmitUser(
            @Field("username") String username, @Field("password") String password
    );

    @FormUrlEncoded
    @POST("tracking/live-location-parent")
    Call<User> LiveLocation(
            @Field("guardian_id") String username
    );

    @FormUrlEncoded
    @POST("tracking/submit-distance/")
    Call<User> SubmitGeoLock(
            @Field("guardian_id") String username, @Field("distance") Double distance
    );
}
