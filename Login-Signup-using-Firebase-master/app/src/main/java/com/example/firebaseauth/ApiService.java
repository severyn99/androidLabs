package com.example.firebaseauth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("solar")   //http get буде йти по шляху solar
    Call<List<Panel>> getPanels();
}
