package com.example.mvp.presenter.favorite

import com.example.mvp.model.Article
import com.example.mvp.model.data.NewsDataSource

class FavoritePresenter(private var view: FavoriteContract.View?, private val dataSource: NewsDataSource) : FavoriteContract.Presenter
{
    fun saveArticle(article: Article)
    {
        dataSource.saveArticle(article)
    }
    fun deleteArticle(article: Article)
    {
        dataSource.deleteArticle(article)
    }

    fun getAll()
    {
        dataSource.getAllArticles(this)
    }

    override fun onSuccess(articles: List<Article>)
    {
        this.view?.showArticles(articles)
    }

    override fun destroyView()
    {
        this.view = null
    }
}