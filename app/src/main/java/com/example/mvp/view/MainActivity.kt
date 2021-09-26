package com.example.mvp.view

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp.R
import com.example.mvp.adapter.MainAdapter
import com.example.mvp.databinding.ActivityMainBinding
import com.example.mvp.model.Article
import com.example.mvp.model.data.NewsDataSource
import com.example.mvp.presenter.news.NewsContract
import com.example.mvp.presenter.news.NewsPresenter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AbstractActivity(), NewsContract.View
{
    private val mainAdapter by lazy { MainAdapter() }
    override lateinit var presenter: NewsPresenter
    override lateinit var binding: ActivityMainBinding

    override fun getLayout() = run {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding
    }

    override fun onInject()
    {
        val dataSource = NewsDataSource(this)
        presenter = NewsPresenter(this, dataSource)
        presenter.requestAll()
        configRecycleView()
        clickAdapter()
    }

    override fun onUninject()
    {
        presenter.destroyView()
    }

    private fun configRecycleView()
    {
        with(binding.rvNews)
        {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun showLoader()
    {
        binding.loaderNews.isVisible = true
    }

    override fun showFailure()
    {
        Snackbar.make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_LONG).show()
    }

    override fun hideLoader()
    {
        binding.loaderNews.isVisible = false
    }

    override fun showArticles(articles: List<Article>)
    {
        mainAdapter.differ.submitList(articles)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.search_menu ->
            {
                val intent = Intent(this, SearchActivity::class.java)
                intent.apply {
                    startActivity(this)
                }
            }
            R.id.favorite_menu ->
            {
                val intent = Intent(this, FavoriteActivity::class.java)
                intent.apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}