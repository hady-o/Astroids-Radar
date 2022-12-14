package com.udacity.asteroidradar.roomdatabase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import kotlinx.coroutines.flow.Flow


@Dao
interface AstroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(asteroids : List<Asteroid>)


    @Query("select * from Asteroid order by closeApproachDate")
    fun getAllAsteroids():Flow<List<Asteroid>>

    @Query("SELECT * from Asteroid where closeApproachDate>= :startDate and closeApproachDate<=:endDate ORDER by closeApproachDate")
    fun getSomeAsteroids(startDate :String,endDate:String): Flow<List<Asteroid>>




    @Database(entities = [Asteroid::class], version = 1, exportSchema = false)
    abstract class AsteroidRoomDataBase: RoomDatabase() {
        abstract val dao : AstroidDao

        companion object {

            @Volatile
            private var Instance: AsteroidRoomDataBase? = null

            fun getInstance(context: Context): AsteroidRoomDataBase {

                synchronized(this)
                {
                    var instance = Instance
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AsteroidRoomDataBase::class.java,
                            "asteroid_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                        Instance = instance
                    }
                    return instance
                }

            }
        }
    }

}