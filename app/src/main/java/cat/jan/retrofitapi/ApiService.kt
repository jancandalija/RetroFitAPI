package cat.jan.retrofitapi

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/2.0/languages")
    fun getLanguages() : Response<List<Language>>
}