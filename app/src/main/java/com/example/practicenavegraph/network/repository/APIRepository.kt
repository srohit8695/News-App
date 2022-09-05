package com.example.practicenavegraph.network.repository

import com.example.practicenavegraph.network.RetrofitService

class APIRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllNews() = retrofitService.getAllNews()
}