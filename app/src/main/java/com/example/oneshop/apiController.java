package com.example.oneshop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiController {

    public static String baseUrl = "https://heard-leaders.000webhostapp.com/";//"http://192.168.100.6:8080/OneShop/";
    private static apiController apiController;
    private static Retrofit retrofit;

    public apiController() {

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                  .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized apiController getInstance()
    {
        if(apiController == null)

            apiController= new apiController();
            return apiController;

    }

  JsonPlaceHolder myapi()
  {
      return retrofit.create(JsonPlaceHolder.class);
  }


//    public static final String baseUrl = "http://192.168.100.6:8080/OneShop/"; //..192.168.43.38
//    public static Retrofit retrofit;
//    public static apiController apiController;
//    apiController()
//    {
//      Gson gson = new GsonBuilder().setLenient().create();
//      retrofit = new Retrofit.Builder()
//              .baseUrl(baseUrl).
//              addConverterFactory(GsonConverterFactory.create())
//              .addConverterFactory(GsonConverterFactory.create(gson))
//              .build();
//    }
//
//    public static synchronized apiController getInstance()
//    {
//       if(apiController == null)
//           apiController = new apiController();
//       return apiController;
//    }
//
//     JsonPlaceHolder myapi()
//     {
//         return  retrofit.create(JsonPlaceHolder.class);
//     }
}
