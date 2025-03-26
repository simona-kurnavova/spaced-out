package com.kurnavova.spacedout.data.spaceflights.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kurnavova.spacedout.data.spaceflights.local.converter.StringListConverter
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity

/**
 * Room database for articles.
 */
@Database(
    entities = [ArticleEntity::class],
    version = ArticleDatabase.Companion.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
internal abstract class ArticleDatabase : RoomDatabase() {
    /**
     * Data access object for articles.
     */
    abstract val articleDao: ArticleDao

    companion object {
        private const val DATABASE_NAME = "article_db"
        private const val DATABASE_VERSION = 1

        fun create(context: Context): ArticleDatabase =
            Room.databaseBuilder(
                context,
                ArticleDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}
