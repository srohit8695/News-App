package com.example.practicenavegraph.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsTable")
data class NewsDataDBEntity(
    val title: String?,
    val imageUrl: String?,
    val content: String?,
    val readMoreUrl: String?,
    @PrimaryKey(autoGenerate = true) val id: Int?= null
)
