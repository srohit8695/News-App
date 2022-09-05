package com.example.practicenavegraph.models


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("category")
    val category: String?,
    @SerializedName("data")
    val newsData: List<NewsData?>?,
    @SerializedName("success")
    val success: Boolean?
)

