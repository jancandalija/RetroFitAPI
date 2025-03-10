package cat.jan.retrofitapi

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @GET("/0.2/languages")
    suspend fun getLanguages() : Response<List<Language>>

    @Headers("Authorization: Bearer 7f85d65503e0c6682085e7e3c762d0a0")
    @FormUrlEncoded
    @POST("/0.2/detect")
    suspend fun getTextLanguage(@Field("q") text:String) : Response<DetectionResponse>
}