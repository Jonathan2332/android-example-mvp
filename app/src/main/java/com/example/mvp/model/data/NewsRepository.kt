package com.example.mvp.model.data

import com.example.mvp.model.Article
import com.example.mvp.model.db.ArticleDatabase

class NewsRepository(private val db: ArticleDatabase)
{
    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll() = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}