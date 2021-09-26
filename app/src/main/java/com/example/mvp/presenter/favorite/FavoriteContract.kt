package com.example.mvp.presenter.favorite

import com.example.mvp.databinding.ActivityFavoriteBinding
import com.example.mvp.model.Article
import com.example.mvp.presenter.BasePresenter
import com.example.mvp.presenter.BaseView

interface FavoriteContract
{
    interface Presenter : BasePresenter
    {
        fun onSuccess(articles: List<Article>)
    }

    interface View : BaseView<FavoritePresenter, ActivityFavoriteBinding>
    {
        fun showArticles(articles: List<Article>)
    }
}