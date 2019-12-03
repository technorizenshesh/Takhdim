package com.techno.takhdim.App;



import com.techno.takhdim.Interfaces.LoadInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.techno.takhdim.App.Config.BASE_URL;


/**
 * Created by user1 on 11/16/2017.
 */

public class AppConfig {
    private static Retrofit retrofit = null;
    private static LoadInterface loadInterface = null;
    public static String getImagePath(String path){
        return "http://www.takhdim-admin.com/uploads/images/"+path;
    }


    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static LoadInterface loadInterface() {
        if (loadInterface == null) {
            loadInterface = AppConfig.getClient().create(LoadInterface.class);
        }
        return loadInterface;
    }


}
