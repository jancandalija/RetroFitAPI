package cat.jan.retrofitapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    const val BASE_URL = "https://ws.detectlanguage.com/"

    val retrofitService: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}