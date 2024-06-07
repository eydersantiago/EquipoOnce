package com.uv.routinesappuv.webService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /* //Esto lo dejo como ejemplo, pero deben modificarlo al igual que las data class de abajo

    @GET("breeds/list/all")
     fun getBreedsList(): Call<DogBreedsResponse>

     @GET("{breed}/images/random")
     fun getImagenPerro(@Path("breed") breed: String): Call<ImageResponse>*/
    @GET("exercises")
    fun getExercises(@Query("limit") limit: Int): Call<List<RoutinesResponse>>
}

data class ImageResponse(
    val message: String,
    val status: String
)

data class RoutinesResponse(
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val name: String
)
