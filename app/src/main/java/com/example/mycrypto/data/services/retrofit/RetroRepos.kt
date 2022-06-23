package com.example.mycrypto.data.services.retrofit

import androidx.lifecycle.MutableLiveData
import com.example.mycrypto.data.models.retrofitmodels.AssetDataModelRetrofit
import com.example.mycrypto.data.models.retrofitmodels.AssetsModelRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetroRepos constructor(private val retrofitService : RetroClient) {

    fun getAssetsApiCallResponse() = retrofitService.getApiService().getAssets()
}