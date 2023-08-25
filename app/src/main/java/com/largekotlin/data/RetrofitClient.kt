package com.largekotlin.data

import android.util.Log
import com.largekotlin.util.MessageProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.security.auth.login.LoginException

const val BASE_URL = "https://favqs.com/"
const val LOG_TAG = "RETROFIT"

class RetrofitClient {
    fun doRequest(responseListener:MessageProvider) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FavQuoteApi::class.java)

        val requestResponse = service.getDayQuote().enqueue(object : Callback<DayQuoteResponse> {
            override fun onResponse(
                call: Call<DayQuoteResponse>,
                response: Response<DayQuoteResponse>
            ) {
                responseListener.showMessage(response.code().toString())
                Log.e(LOG_TAG, response.code().toString())

                if (response.code()==200){
                    val responseBody = response.body()
                    if (responseBody!=null){
                        responseListener.showMessage(responseBody.quote.body)
                    }
                }
            }

            override fun onFailure(call: Call<DayQuoteResponse>, t: Throwable) {
                responseListener.showMessage(t.message.toString())
            }
        })
    }
}

interface FavQuoteApi {
    @GET("api/qotd")
    fun getDayQuote(): Call<DayQuoteResponse>
}

data class DayQuoteResponse(
    val qotd_date: String,
    val quote:Quote
)

data class Quote(
    val id: Int,
    val author: String,
    val body: String
)
