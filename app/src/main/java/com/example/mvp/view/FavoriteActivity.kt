package com.example.mvp.view

import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvp.adapter.MainAdapter
import com.example.mvp.databinding.ActivityFavoriteBinding
import com.example.mvp.model.Article
import com.example.mvp.model.data.NewsDataSource
import com.example.mvp.presenter.favorite.FavoriteContract
import com.example.mvp.presenter.favorite.FavoritePresenter
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity  : AbstractActivity(), FavoriteContract.View
{
    private val mainAdapter by lazy { MainAdapter() }
    override lateinit var presenter: FavoritePresenter
    override lateinit var binding: ActivityFavoriteBinding

    override fun getLayout() = run {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        binding
    }

    override fun onInject()
    {
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)
        presenter.getAll()
        configRecycleView()
        clickAdapter()

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
            {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                val adapterPosition = viewHolder.adapterPosition
                val article = mainAdapter.differ.currentList[adapterPosition]
                presenter.deleteArticle(article)
                Snackbar.make(viewHolder.itemView, "Successful deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo")
                    {
                        presenter.saveArticle(article)
                        mainAdapter.notifyDataSetChanged()
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        presenter.getAll()
    }

    override fun onUninject()
    {
        presenter.destroyView()
    }

    override fun showArticles(articles: List<Article>)
    {
        this.mainAdapter.differ.submitList(articles)
    }

    private fun configRecycleView()
    {
        with(binding.rvFavorite)
        {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(DividerItemDecoration(this@FavoriteActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun clickAdapter()
    {
        mainAdapter.setOnClickListener {
            val intent = Intent(this, ArticleActivity::class.java)
            intent.apply {
                putExtra("article", it)
                startActivity(intent)
            }
        }
    }
}