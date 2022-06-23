package com.example.mycrypto.data.services.retrofit

import com.example.mycrypto.ui.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroClient {

    private var retrofit : Retrofit? = null
    private var okHttpClient : OkHttpClient? = null
    private var mRetrofitApi : RetrofitApi? = null

    fun getApiService(): RetrofitApi {
        if(mRetrofitApi == null)
            mRetrofitApi = getRetrofitClient()!!.create(RetrofitApi::class.java)

        return mRetrofitApi as RetrofitApi
    }

    private fun getRetrofitClient() : Retrofit? {
        if(okHttpClient == null) initOkHttp()

        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.kBaseUrl)
                .client(okHttpClient!!)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }

    companion object {
        private var mRetroClient : RetroClient? = null
        fun getInstance() : RetroClient {
            if (mRetroClient == null) mRetroClient = RetroClient()

            return mRetroClient!!
        }
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
        val interceptor  = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("content-type","application/json")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()

    }

}