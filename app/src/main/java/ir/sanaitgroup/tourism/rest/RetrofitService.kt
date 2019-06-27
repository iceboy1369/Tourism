package ir.sanaitgroup.tourism.rest

import com.google.gson.JsonObject
import ir.sanaitgroup.tourism.models.MainCompanyInfo
import ir.sanaitgroup.tourism.models.MainLogin
import ir.sanaitgroup.tourism.models.MainServiceLevel
import ir.sanaitgroup.tourism.models.MainServices
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    //urls
    @Headers("APIKEY: API1","Content-Type: application/json")
    @POST("getUserInfo")
    fun getUsers(@Body json: String): Call<MainLogin>


    @Headers("APIKEY: API1","Content-Type: application/json")
    @POST("getInfoTouristAttractions")
    fun getInfoTouristAttractions(@Body json: String): Call<MainCompanyInfo>


    @Headers("APIKEY: API1","Content-Type: application/json")
    @POST("getTourismServices")
    fun getServices(@Body json: String ): Call<MainServices>


    @Headers("APIKEY: API1", "Content-Type: application/json")
    @POST("getScheduleAvailable")
    fun getScheduleAvailable(@Body json: JsonObject): Call<MainServiceLevel>

}