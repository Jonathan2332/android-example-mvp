package com.example.mvp.presenter.news

import com.example.mvp.model.NewsResponse
import com.example.mvp.model.data.NewsDataSource

class NewsPresenter(private var view: NewsContract.View?, private val dataSource: NewsDataSource) : NewsContract.Presenter
{
    override fun requestAll()
    {
        this.view?.showLoader()
        this.dataSource.getBreakingNews(this)
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