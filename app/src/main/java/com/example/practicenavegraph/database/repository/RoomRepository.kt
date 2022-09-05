package com.example.practicenavegraph.database.repository

import android.content.Context
import com.example.practicenavegraph.database.RoomService
import com.example.practicenavegraph.database.dao.NewsDAO
import com.example.practicenavegraph.database.entity.NewsDataDBEntity

class RoomRepository(context: Context) {

    var dbms : NewsDAO = RoomService.getInstance(context)?.newsDao()!!

    fun insertData(newsDataDBEntity: NewsDataDBEntity) : Long{
        return dbms.insertNewsData(newsDataDBEntity)
    }

    fun insertAllData(newsDataDBEntity: List<NewsDataDBEntity>) {
        return dbms.insertAllNewsData(newsDataDBEntity)
    }

    fun deleteAllData() {
        return dbms.deleteAllNewsData()
    }

    fun getAllNews() : List<NewsDataDBEntity>{
        return dbms.getAllNews()
    }

    fun getRowCountFromDB() : Int{
        return dbms.getDBRowCount()
    }

}