package com.example.mvp.view

import android.content.Intent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp.adapter.MainAdapter
import com.example.mvp.databinding.ActivitySearchBinding
import com.example.mvp.model.Article
import com.example.mvp.model.data.NewsDataSource
import com.example.mvp.presenter.search.SearchContract
import com.example.mvp.presenter.search.SearchPresenter
import com.example.mvp.util.QueryTextListenerUtil
import com.google.android.material.snackbar.Snackbar

class SearchActivity : AbstractActivity(), SearchContract.View
{
    private val mainAdapter by lazy { MainAdapter() }
    override lateinit var presenter: SearchPresenter
    override lateinit var binding: ActivitySearchBinding

    override fun getLayout() = run {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        binding
    }

    override fun onInject()
    {
        val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this, dataSource)
        configRecycleView()
        search()
        clickAdapter()
    }

    override fun onUninject()
    {
        presenter.destroyView()
    }

    private fun search()
    {
        binding.searchView.setOnQueryTextListener(QueryTextListenerUtil(this.lifecycle) {
            it?.let {
                if(it.isNotEmpty())
                {
                    presenter.search(it)
                    showLoader()
                }
            }
        })
    }

    private fun configRecycleView()
    {
        with(binding.rvSearch)
        {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(DividerItemDecoration(this@SearchActivity, DividerItemDecoration.VERTICAL))
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

    override fun showLoader()
    {
        binding.loaderNewsSearch.isVisible = false
    }

    override fun showFailure()
    {
        Snackbar.make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_LONG).show()
    }

    override fun hideLoader()
    {
        binding.loaderNewsSearch.isVisible = true
    }

    override fun showArticles(articles: List<Article>)
    {
        mainAdapter.differ.submitList(articles)
    }
}