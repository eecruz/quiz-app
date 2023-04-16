package edu.quinnipiac.ser210.witswarzone

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.*

interface ApiInterface
{
    // retrieves questions from API
    @Headers("Accept:application/json", "Content-Type:application/json",
        "X-RapidAPI-Key:d859bd9d8emsh7665e099afce6e3p13227djsnf07373f301cb",
        "X-RapidAPI-Host:trivia-by-api-ninjas.p.rapidapi.com")
    @GET("v1/trivia")
    fun getQuestions(@Query("category") category: String, @Query("limit") limit: Int) : Call<List<Question>>

    companion object
    {
        var BASE_URL = "https://trivia-by-api-ninjas.p.rapidapi.com/"

        fun create(): ApiInterface
        {
            // adds api fetch to logcat
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            // builds retrofit
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client.build())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}