package com.example.practicenavegraph.ui.homeScreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicenavegraph.R
import com.example.practicenavegraph.database.entity.NewsDataDBEntity
import com.example.practicenavegraph.databinding.TempletNewsListBinding

class NewsAdapter(val context: Context, var newsList : ArrayList<NewsDataDBEntity>, private val readMoreListener: (String) -> Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TempletNewsListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TempletNewsListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            with(holder){
                val tempNewsItem = newsList[position]
                binding.newsDescription.text = tempNewsItem.content
                binding.newsTitle.text = tempNewsItem.title

                Glide.with(context)
                    .load(tempNewsItem.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.thumbnail_image)
                    .into(binding.newsImage)

                binding.readMore.setOnClickListener {
                    val readURL = tempNewsItem.readMoreUrl

                    if(readURL == null) {
                        Toast.makeText(context, "No URL present", Toast.LENGTH_SHORT).show()
                    }else{
                        readMoreListener.invoke(readURL)
                    }



                        tempNewsItem.readMoreUrl?.let { it1 -> readMoreListener.invoke(it1) }

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun updateNewsData(updatedNewsList: List<NewsDataDBEntity>){
        newsList.addAll(updatedNewsList)
        notifyDataSetChanged()
    }


}