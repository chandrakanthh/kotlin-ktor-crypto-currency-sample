package com.example.mycrypto.data.services.retrofit

import com.example.mycrypto.data.models.retrofitmodels.AssetDataModelRetrofit
import com.example.mycrypto.data.models.retrofitmodels.AssetsModelRetrofit
import com.example.mycrypto.ui.utils.Constants
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {

    @GET(Constants.kAssets)
    fun getAssets() : Call<AssetDataModelRetrofit>
}