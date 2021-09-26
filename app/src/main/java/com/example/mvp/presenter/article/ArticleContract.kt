package com.example.mvp.presenter.article

import com.example.mvp.databinding.ActivityArticleBinding
import com.example.mvp.presenter.BaseView

interface ArticleContract
{
    interface View : BaseView<ArticlePresenter, ActivityArticleBinding>
    {
        fun showSuccess()
    }
}