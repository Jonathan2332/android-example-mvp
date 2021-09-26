package com.example.mvp.presenter.news

import com.example.mvp.databinding.ActivityMainBinding
import com.example.mvp.model.Article
import com.example.mvp.model.NewsResponse
import com.example.mvp.presenter.BasePresenter
import com.example.mvp.presenter.BaseView

interface NewsContract : BasePresenter
{
    interface Presenter : BasePresenter
    {
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }

    interface View : BaseView<NewsPresenter, ActivityMainBinding>
    {
        fun showLoader()
        fun showFailure()
        fun hideLoader()
        fun showArticles(articles: List<Article>)
    }
}