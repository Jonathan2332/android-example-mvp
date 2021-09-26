package com.example.mvp.presenter.article

import com.example.mvp.model.Article
import com.example.mvp.model.data.NewsDataSource
import com.example.mvp.presenter.favorite.FavoriteContract

class ArticlePresenter(private var view: ArticleContract.View?, private val dataSource: NewsDataSource) : FavoriteContract.Presenter
{
    fun saveArticle(article: Article)
    {
        dataSource.saveArticle(article)
    }

    override fun onSuccess(articles: List<Article>)
    {
        this.view?.showSuccess()
    }

    override fun destroyView()
    {
        this.view = null
    }
}