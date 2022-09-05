package com.example.practicenavegraph.utility

import com.example.practicenavegraph.models.APIResponseStatus


typealias OnAPIResponse = (responseData : APIResponseStatus) -> Unit
typealias OnThrowableResponse = (throwable : Throwable) -> Unit