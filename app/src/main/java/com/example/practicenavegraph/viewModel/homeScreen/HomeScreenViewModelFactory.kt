package com.example.practicenavegraph.viewModel.homeScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicenavegraph.database.repository.RoomRepository
import com.example.practicenavegraph.network.repository.APIRepository

class HomeScreenViewModelFactory constructor (private val apiRepository : APIRepository, private val dbRepository : RoomRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            HomeScreenViewModel(this.apiRepository, this.dbRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}

