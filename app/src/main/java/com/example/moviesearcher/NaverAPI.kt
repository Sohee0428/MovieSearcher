package com.example.moviesearcher

import android.util.Log
import android.widget.EditText
import com.example.moviesearcher.data.ResultGetSearchMovie
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverAPI {
    @GET("v1/search/movie.json")
    fun getSearchMovies(
        @Query("query") query: String
    ): Call<ResultGetSearchMovie>

    companion object {
        private const val BASE_URL_NAVER_API = "https://openapi.naver.com/"
        private const val CLIENT_ID = "7HOJNcmQsYMZjMHDndyK"
        private const val CLIENT_SECRET = "XOB__trP_C"

        fun create(): NaverAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL_NAVER_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NaverAPI::class.java)

        }
    }
}