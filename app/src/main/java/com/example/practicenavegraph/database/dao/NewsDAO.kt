package com.example.practicenavegraph.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicenavegraph.database.entity.NewsDataDBEntity

@Dao
interface NewsDAO {

    @Insert
    fun insertNewsData(newsDataDBEntity: NewsDataDBEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNewsData(newsDataDBEntity: List<NewsDataDBEntity>)

    @Query("DELETE FROM newsTable")
    fun deleteAllNewsData()

    @Query("SELECT * FROM newsTable")
    fun getAllNews(): List<NewsDataDBEntity>

    @Query("SELECT COUNT(*) FROM newsTable")
    fun getDBRowCount() : Int

}