package com.example.mvp.model.data

import android.content.Context
import com.example.mvp.model.Article
import com.example.mvp.model.db.ArticleDatabase
import com.example.mvp.network.RetrofitInstance
import com.example.mvp.presenter.favorite.FavoriteContract
import com.example.mvp.presenter.search.SearchContract
import com.example.mvp.presenter.news.NewsContract
import kotlinx.coroutines.*

class NewsDataSource(context: Context)
{
    private val db = ArticleDatabase(context)
    private val newsRepository = NewsRepository(db)

    fun getBreakingNews(callback: NewsContract.Presenter)
    {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews()
            if(response.isSuccessful)
            {
                response.body()?.let {
                    callback.onSuccess(it)
                }
            }
            else
            {
                callback.onError(response.message())
            }
            callback.onComplete()
        }
    }

    fun searchNews(query: String, callback: SearchContract.Presenter)
    {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.searchNews(query)
            if(response.isSuccessful)
            {
                response.body()?.let {
                    callback.onSuccess(it)
                }
            }
            else
            {
                callback.onError(response.message())
            }
            callback.onComplete()
        }
    }

    fun saveArticle(article: Article)
    {
        GlobalScope.launch(Dispatchers.Main) {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticles(callback: FavoriteContract.Presenter)
    {
        var allArticles: List<Article>
        GlobalScope.launch(Dispatchers.IO)
        {
            allArticles = newsRepository.getAll()

            withContext(Dispatchers.Main)
            {
                callback.onSuccess(allArticles)
            }
        }
    }

    fun deleteArticle(article: Article?)
    {
        GlobalScope.launch(Dispatchers.Main) {
            article?.let {
                newsRepository.delete(article)
            }
        }
    }
}