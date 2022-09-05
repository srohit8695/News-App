package com.example.practicenavegraph.ui.homeScreen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicenavegraph.R
import com.example.practicenavegraph.database.entity.NewsDataDBEntity
import com.example.practicenavegraph.database.repository.RoomRepository
import com.example.practicenavegraph.databinding.FragmentHomeScreenBinding
import com.example.practicenavegraph.models.APIResponseStatus
import com.example.practicenavegraph.network.RetrofitService
import com.example.practicenavegraph.network.repository.APIRepository
import com.example.practicenavegraph.ui.homeScreen.adapters.NewsAdapter
import com.example.practicenavegraph.viewModel.homeScreen.HomeScreenViewModel
import com.example.practicenavegraph.viewModel.homeScreen.HomeScreenViewModelFactory

class HomeScreenFragment : Fragment(),LifecycleObserver {

    private lateinit var viewModel: HomeScreenViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var roomRepository : RoomRepository
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var newsAdapter: NewsAdapter
    private var newsList = ArrayList<NewsDataDBEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomRepository = context?.let { RoomRepository(it) }!!
        viewModel = ViewModelProvider(this, HomeScreenViewModelFactory(APIRepository(retrofitService),roomRepository))[HomeScreenViewModel::class.java]

        binding.newsRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        newsAdapter = NewsAdapter(requireContext(),newsList){
//            Toast.makeText(context,it, Toast.LENGTH_LONG).show()
            val bundle = bundleOf("newsURL" to it)
            Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_newsDetailFragment,bundle)

        }
        binding.newsRecyclerView.adapter = newsAdapter

        viewModel.makeDMEmpty()

        viewModel.getAllNews({
            when (it) {
                APIResponseStatus.FAILURE -> {
                    println("API Response : No data from API")
                }
                APIResponseStatus.SUCCESS -> {
                    println("API Response : Data loaded successfully")
                }
            }
        },{
            println("API Response : Throwable "+it.message)
        })



        viewModel.newsDBList.observe(viewLifecycleOwner){
            newsAdapter.updateNewsData(it as List<NewsDataDBEntity>)
        }

    }

}