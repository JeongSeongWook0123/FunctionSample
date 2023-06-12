package com.jsu.functionapp

import com.jsu.functionapp.util.Constants
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitSample {

    @POST("/test/server/url")
    fun getSample(): Single<RetrofitSample>

    @Multipart
    @POST("/test/server/url")
    fun sendCDataSample(
        @Part filed: ArrayList<MultipartBody.Part>?,
        @Part("CustIdx") CustIdx: RequestBody,
        @Part("SessionKey") InstCd: RequestBody,
        @Part("Content") Content: RequestBody
    ): Single<RetrofitSample>

    companion object Factory {

        fun create(strUrl:String = Constants.serverUrl): RetrofitSample {
            val builder = OkHttpClient.Builder()


            builder.addInterceptor {
                val request = it.request().newBuilder().addHeader("User-Agent","My-App-Android").build()
                it.proceed(request)
            }

            val client = builder.build()

            val services = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(strUrl)
                .client(client)
                .build()

            return services.create(RetrofitSample::class.java)

        }


    }
}