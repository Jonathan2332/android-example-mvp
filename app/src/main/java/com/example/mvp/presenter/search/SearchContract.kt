package com.example.mvp.presenter.search

import com.example.mvp.databinding.ActivitySearchBinding
import com.example.mvp.model.Article
import com.example.mvp.model.NewsResponse
import com.example.mvp.presenter.BasePresenter
import com.example.mvp.presenter.BaseView

interface SearchContract
{
    interface Presenter : BasePresenter
    {
        fun search(query: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }

    interface View : BaseView<SearchPresenter, ActivitySearchBinding>
    {
        fun showLoader()
        fun showFailure()
        fun hideLoader()
        fun showArticles(articles: List<Article>)
    }
}