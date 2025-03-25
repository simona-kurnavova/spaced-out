package com.kurnavova.spacedout.data.spaceflights.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = ArticleDatabase.Companion.DATABASE_VERSION,
    exportSchema = false
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao

    companion object {
        private const val DATABASE_NAME = "article_db"
        private const val DATABASE_VERSION = 1

        fun create(context: Context): ArticleDatabase {
            return Room.databaseBuilder(
                context,
                ArticleDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
