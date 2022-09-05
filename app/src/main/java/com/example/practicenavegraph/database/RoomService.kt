package com.example.practicenavegraph.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practicenavegraph.database.dao.NewsDAO
import com.example.practicenavegraph.database.entity.NewsDataDBEntity

@Database(entities = [NewsDataDBEntity::class], version = 1)
@TypeConverters
abstract class RoomService : RoomDatabase(){

    abstract fun newsDao() : NewsDAO

    companion object{

        private var instance : RoomService ? = null

        fun getInstance(context: Context) : RoomService?{
            if(instance == null){
                synchronized(RoomService::class){
                    instance = Room.databaseBuilder(context.applicationContext, RoomService::class.java, "news.db")
                        .allowMainThreadQueries().build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }

}