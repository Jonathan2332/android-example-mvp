package com.example.mvp.view

import android.webkit.WebViewClient
import com.example.mvp.databinding.ActivityArticleBinding
import com.example.mvp.model.Article
import com.example.mvp.model.data.NewsDataSource
import com.example.mvp.presenter.article.ArticleContract
import com.example.mvp.presenter.article.ArticlePresenter
import com.google.android.material.snackbar.Snackbar

class ArticleActivity  : AbstractActivity(), ArticleContract.View
{
    private lateinit var article: Article

    override lateinit var presenter: ArticlePresenter
    override lateinit var binding: ActivityArticleBinding

    override fun getLayout() = run {
        binding = ActivityArticleBinding.inflate(layoutInflater)
        binding
    }

    override fun onInject()
    {
        getArticle()

        val dataSource = NewsDataSource(this)
        presenter = ArticlePresenter(this, dataSource)

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener {
            presenter.saveArticle(article)
        }
    }

    override fun onUninject()
    {
        presenter.destroyView()
    }

    private fun getArticle()
    {
        intent.extras?.let {
            article = it.get("article") as Article
        }
    }

    override fun showSuccess()
    {
        Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG).show()
    }
}