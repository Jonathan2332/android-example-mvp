package com.example.mvp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvp.databinding.ItemNewsBinding
import com.example.mvp.model.Article

class MainAdapter : RecyclerView.Adapter<MainAdapter.ArticleViewHolder>()
{
    inner class ArticleViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Article>()
    {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean
        {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean
        {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(
        ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int)
    {
        with(holder)
        {
            with(differ.currentList[position])
            {
                Glide.with(itemView.context).load(urlToImage).into(binding.newsImage)
                binding.newsTitle.text = author ?: source?.name
                binding.newsPubDate.text = publishedAt
                binding.newsDescription.text = description
                binding.newsSource.text = source?.name ?: author

                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { article ->
                        article(this)
                    }
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener : ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}