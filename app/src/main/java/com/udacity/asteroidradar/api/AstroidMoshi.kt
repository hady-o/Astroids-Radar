package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.internal.Intrinsics
const val val1 ="neo/rest/v1/feed"
const val val2 ="planetary/apod"



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

private val asteroidService: AsteroidsApiService by lazy {
    retrofit.create(AsteroidsApiService::class.java)
}


interface AsteroidsApiService {

    @GET(val2)
    suspend fun getImageOfThisDay(@Query("api_key")apiKey:String):PictureOfDay



    @GET(val1)
    suspend fun getAllAsteroids(
        @Query("api_key")apiKey:String): String


    object Net{
       suspend fun getAsteroidsList():List<Asteroid> {
                val asteroidRes=asteroidService.getAllAsteroids(Constants.api_key)
                val asteroidsList= parseAsteroidsJsonResult(JSONObject(asteroidRes))
                return asteroidsList
        }

        suspend fun getImage():PictureOfDay
        {
            return asteroidService.getImageOfThisDay(Constants.api_key)
        }

    }
}

