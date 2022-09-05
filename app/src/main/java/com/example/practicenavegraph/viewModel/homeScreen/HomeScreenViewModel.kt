package com.example.practicenavegraph.viewModel.homeScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicenavegraph.database.entity.NewsDataDBEntity
import com.example.practicenavegraph.database.repository.RoomRepository
import com.example.practicenavegraph.models.NewsData
import com.example.practicenavegraph.models.NewsResponse
import com.example.practicenavegraph.models.APIResponseStatus
import com.example.practicenavegraph.network.repository.APIRepository
import com.example.practicenavegraph.utility.OnAPIResponse
import com.example.practicenavegraph.utility.OnThrowableResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenViewModel(private val repository: APIRepository,private val dbRepository: RoomRepository) : ViewModel() {

    val newsAPIList = MutableLiveData<List<NewsData?>?>()
    val newsDBList = MutableLiveData<List<NewsDataDBEntity?>?>()
    val errorMessage = MutableLiveData<String>()

    fun makeDMEmpty(){
//        println("DB Row Count : "+dbRepository.getRowCountFromDB())
        if(dbRepository.getRowCountFromDB() != 0){
            dbRepository.deleteAllData()
        }
    }

    fun getAllNews(onAPIResponse : OnAPIResponse, onThrowableResponse: OnThrowableResponse) {
        val response = repository.getAllNews()
        response.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.body()?.success!!) {

                    onAPIResponse.invoke(APIResponseStatus.SUCCESS)
                    println("API Response : "+response.body()?.newsData.toString())
                    newsAPIList.postValue(response.body()?.newsData)

                    for(tempList in response.body()?.newsData!!){
                        val localNews = NewsDataDBEntity(tempList!!.title, tempList.imageUrl, tempList.content, tempList.readMoreUrl)
                        dbRepository.insertData(localNews)
                    }
//                    println("DB response : "+dbRepository.getAllNews().toString())
                    loadFromLocalDataBase()
                } else {
                    onAPIResponse.invoke(APIResponseStatus.FAILURE)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                onThrowableResponse.invoke(t)
            }
        })
    }

    fun loadFromLocalDataBase(){
        val localDBData = dbRepository.getAllNews()
        /*for(localData in localDBData){
            println("News in DB : $localData")
        }*/

        newsDBList.postValue(localDBData)

//        println("DB Row Count : "+dbRepository.getRowCountFromDB())
    }

}