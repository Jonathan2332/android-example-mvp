package com.example.mvp.presenter.search

import com.example.mvp.model.NewsResponse
import com.example.mvp.model.data.NewsDataSource

class SearchPresenter(private var view: SearchContract.View?, private val dataSource: NewsDataSource) : SearchContract.Presenter
{
    override fun search(query: String)
    {
        this.view?.showLoader()
        this.dataSource.searchNews(query, this)
    }

    override fun onSuccess(newsResponse: NewsResponse)
    {
        this.view?.showArticles(newsResponse.articles)
    }

    override fun onError(message: String)
    {
        this.view?.showFailure()
    }

    override fun onComplete()
    {
        this.view?.hideLoader()
    }

    override fun destroyView()
    {
        this.view = null
    }
}