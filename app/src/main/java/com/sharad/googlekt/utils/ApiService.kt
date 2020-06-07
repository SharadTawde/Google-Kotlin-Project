package com.sharad.googlekt.utils

import com.sharad.googlekt.model.StateWise
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("data.json")
    suspend fun getData(): Response<StateWise>

    companion object {
        const val BASE_URL = "https://api.covid19india.org/"
    }
}
