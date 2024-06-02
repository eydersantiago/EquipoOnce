package com.uv.routinesappuv.webService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
   /* //Esto lo dejo como ejemplo, pero deben modificarlo al igual que las data class de abajo

   @GET("breeds/list/all")
    fun getBreedsList(): Call<DogBreedsResponse>

    @GET("{breed}/images/random")
    fun getImagenPerro(@Path("breed") breed: String): Call<ImageResponse>*/
}

data class ImageResponse(
    val message: String,
    val status: String
)

data class RoutinesResponse(
    val message: Map<String,
            List<String>>,
    val status: String
)