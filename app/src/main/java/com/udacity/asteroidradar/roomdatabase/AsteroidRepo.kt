package com.udacity.asteroidradar.roomdatabase

import android.net.Network
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.channels.NetworkChannel

class AsteroidRepo(val dataBase: AstroidDao.AsteroidRoomDataBase) {
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshData(){
        withContext(Dispatchers.IO){
            val allAsteroids = AsteroidsApiService.Net.getAsteroidsList()
            dataBase.dao.insertAllAsteroids((allAsteroids))
        }
    }
}