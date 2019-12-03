package com.techno.takhdim.Interfaces;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ritesh on 29/9/18.
 */

public interface LoadInterface {

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("login")
    Call<ResponseBody> login(@Query("mobile") String mobile,
                             @Query("lat") String lat,
                             @Query("lon") String lon,
                             @Query("type") String type,
                             @Query("register_id") String register_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("verify_otp")
    Call<ResponseBody> verify_otp(@Query("user_id") String user_id,
                                  @Query("otp") String otp);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("category")
    Call<ResponseBody> category();


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("services_list")
    Call<ResponseBody> services_list(@Query("category_id") String category_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_user_part_booking")
    Call<ResponseBody> get_user_part_booking(@Query("user_id") String user_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_profile")
    Call<ResponseBody> get_profile(@Query("user_id") String user_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_service_field")
    Call<ResponseBody> get_service_field(@Query("service_id") String service_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update_profile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    @POST("user_signup")
    Call<ResponseBody> user_signup(@Query("username") String username,
                              @Query("mobile") String mobile,
                              @Query("email") String email,
                              @Query("types") String type,
                              @Query("register_id") String register_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update_profile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    @POST("get_request")
    Call<ResponseBody> get_request(@Query("user_id") String user_id,
                                   @Query("status") String status);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update_profile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    @POST("get_request_offer")
    Call<ResponseBody> get_request_offer(@Query("request_id") String request_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update_profile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @Multipart
    @POST("update_profile")
    Call<ResponseBody> updateProfile(@Query("user_id") String user_id,
                                     @Query("username") String username,
                                     @Query("mobile") String mobile,
                                     @Query("email") String email,
                                     @Query("address") String address,
                                     @Part MultipartBody.Part body);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update_profile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @Multipart
    @POST("request_nearby_provider")
    Call<ResponseBody> request_nearby_provider(@Query("service_id") String service_id,
                                     @Query("user_id") String user_id,
                                     @Query("date") String date,
                                     @Query("time") String time,
                                     @Query("address") String address,
                                     @Query("lat") String lat,
                                     @Query("lon") String lon,
                                     @Query("house_unit") String house_unit,
                                     @Query("details") String details,
                                     @Query("field_data") String field_data,
                                     @Part MultipartBody.Part body1,
                                     @Part MultipartBody.Part body2,
                                     @Part MultipartBody.Part body3,
                                     @Part MultipartBody.Part body4);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_request_details")
    Call<ResponseBody> get_request_details(@Query("request_id") String request_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_conversation")
    Call<ResponseBody> getConversetion(@Query("sender_id") String sender_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("get_chat")
    Call<ResponseBody> getChat1(@Query("sender_id") String sender_id,
                                @Query("receiver_id") String receiver_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("book_part")
    Call<ResponseBody> book_part(@Query("user_id") String user_id,
                                @Query("part_id") String part_id,
                                @Query("provider_id") String provider_id,
                                @Query("quantity") String quantity);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("insert_chat")
    Call<ResponseBody> insertChat(@Query("sender_id") String sender_id,
                                  @Query("receiver_id") String receiver_id,
                                  @Query("chat_message") String chat_message);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("accept_offer")
    Call<ResponseBody> accept_offer(@Query("offer_id") String mobile);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_parts")
    Call<ResponseBody> get_parts(@Query("cat_id") String cat_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_part_by_id")
    Call<ResponseBody> get_part_by_id(@Query("part_id") String part_id);
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% get_comment %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_comment")
    Call<ResponseBody> getComment(@Query("service_id") String service_id);
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% add_comment %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("add_comment")
    Call<ResponseBody> addComment(@Query("service_id") String service_id,@Query("user_id") String user_id,@Query("comment") String comment);
}
