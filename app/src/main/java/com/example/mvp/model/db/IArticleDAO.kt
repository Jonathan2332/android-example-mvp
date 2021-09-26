package com.example.mvp.model.db

import androidx.room.*
import com.example.mvp.model.Article

@Dao
interface IArticleDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article) : Long

    @Query("SELECT * from articles")
    fun getAll() : List<Article>

    @Delete
    suspend fun delete(article: Article)
}