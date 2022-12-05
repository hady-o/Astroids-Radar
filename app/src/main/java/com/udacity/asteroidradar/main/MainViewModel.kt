package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.Constants.getThisDay
import com.udacity.asteroidradar.Constants.getWeekDate
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidsApiService
import com.udacity.asteroidradar.roomdatabase.AsteroidRepo
import com.udacity.asteroidradar.roomdatabase.AstroidDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val database = AstroidDao.AsteroidRoomDataBase.getInstance(application)
    private val repository = AsteroidRepo(database)

    private val _allAsteroids = MutableLiveData<List<Asteroid>>()
    val allAsteroids : LiveData<List<Asteroid>>
    get() = _allAsteroids

    private val _image = MutableLiveData<PictureOfDay>()
    val image : LiveData<PictureOfDay>
        get() = _image

    init {
        getImagee()
        refresh()
        getAll()

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refreshData()
            }catch (e:Exception)
                {
                }
            }
        }

       fun getAll() {
        viewModelScope.launch {
            database.dao.getAllAsteroids().collect(){
                _allAsteroids.value = it
        }
    }

    }

    fun getWeek() {
        viewModelScope.launch {
            database.dao.getSomeAsteroids(getThisDay(), getWeekDate()).collect(){
                _allAsteroids.value = it
            }
        }

    }


    fun getday() {
        viewModelScope.launch {
            database.dao.getSomeAsteroids(getThisDay(), getThisDay()).collect(){
                _allAsteroids.value = it
            }
        }

    }

  fun getImagee() {
        viewModelScope.launch {
           try {
               _image.value= AsteroidsApiService.Net.getImage()
           }catch (e:Exception){
            }
        }

    }
};