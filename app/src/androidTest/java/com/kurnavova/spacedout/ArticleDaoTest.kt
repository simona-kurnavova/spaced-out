package com.kurnavova.spacedout

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kurnavova.spacedout.data.spaceflights.local.ArticleDao
import com.kurnavova.spacedout.data.spaceflights.local.ArticleDatabase
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.collections.forEach

/**
 * Tests for [ArticleDao].
 */
@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {
    private lateinit var articleDao: ArticleDao
    private lateinit var database: ArticleDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            ArticleDatabase::class.java
        ).build()

        articleDao = database.articleDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertArticles(): Unit = runTest {
        val articles = createTestingArticles(10)
        articleDao.insertArticles(articles)

        // Verify that the articles are inserted correctly
        articles.forEach {
            val insertedArticle = articleDao.getById(it.id)

            assertNotNull(insertedArticle)
            Assert.assertEquals(it, insertedArticle)
        }
    }

    @Test
    fun testDeleteOlderThan() = runTest {
        val articles = createTestingArticles(5)
        articleDao.insertArticles(articles)

        val thresholdDate = "2025-03-23"

        // Delete articles older than the threshold date
        articleDao.deleteOlderThan(thresholdDate)

        for (i in 0 until articles.size) {
            val article = articles[i]
            if (article.date < thresholdDate) {
                // Articles older than the threshold should be deleted
                val deletedArticle = articleDao.getById(article.id)
                assertNull(deletedArticle)
            } else {
                // Articles on or after the threshold should be kept
                val keptArticle = articleDao.getById(article.id)
                assertNotNull(keptArticle)
            }
        }
    }

    @Test
    fun testGetPagedArticles() = runTest {
        val articles = createTestingArticles(20)
        articleDao.insertArticles(articles)

        val pagingSource = articleDao.getPagedArticles()

        val loadSize = 10
        val result = pagingSource.load(
            LoadParams.Refresh(
                key = null, // Starting from the first page (null)
                loadSize = loadSize, // Load 2 articles per page
                placeholdersEnabled = false
            )
        )

        assertTrue(result is LoadResult.Page)
        val page = result as LoadResult.Page

        Assert.assertEquals(loadSize, page.data.size)

        result.zipWithNext().forEach {
            assertTrue(it.first.date >= it.second.date)
        }

        val secondLoadSize = 5

        val secondPageResult = pagingSource.load(
            LoadParams.Refresh(
                key = page.nextKey, // Use the next key provided by the first page
                loadSize = secondLoadSize,
                placeholdersEnabled = false
            )
        )

        assertTrue(secondPageResult is LoadResult.Page)

        val secondPage = secondPageResult as LoadResult.Page
        Assert.assertEquals(secondLoadSize, secondPage.data.size)

        secondPageResult.zipWithNext().forEach {
            assertTrue(it.first.date >= it.second.date)
        }
    }

    private fun createTestingArticles(number: Int): List<ArticleEntity> =
        (0 until number).map {
            ArticleEntity(
                id = it,
                title = "Article $it",
                summary = "Summary $it",
                url = "http://example.com/$it",
                imageUrl = "http://example.com/image$it.jpg",
                author = "${it}",
                date = "2025-03-${25 - it}",
            )
        }
}
