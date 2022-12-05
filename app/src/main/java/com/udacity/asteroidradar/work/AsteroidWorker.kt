package com.udacity.asteroidradar.work

import android.content.Context
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.roomdatabase.AsteroidRepo
import com.udacity.asteroidradar.roomdatabase.AstroidDao
import retrofit2.HttpException
import java.net.HttpRetryException

class AsteroidWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object{
        const val Work_Name = "AsteroidWorker"
    }

    override suspend fun doWork(): Result {
         val database = AstroidDao.AsteroidRoomDataBase.getInstance(applicationContext)
         val repository = AsteroidRepo(database)

        return try
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                repository.refreshData()
            }
            Result.success()
        }catch (e: HttpException)
        {
            Result.retry()
        }


    }
}