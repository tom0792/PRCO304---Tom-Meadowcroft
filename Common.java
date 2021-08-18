package com.example.parkhappy3;

import com.example.parkhappy3.Model.MyPlaces;
import com.example.parkhappy3.Model.Results;
import com.example.parkhappy3.Remote.IGoogleAPIService;
import com.example.parkhappy3.Remote.RetrofitClient;

public class Common {


    public static Results currentResult;
    

 
    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static IGoogleAPIService getGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}


