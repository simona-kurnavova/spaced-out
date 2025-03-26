package com.kurnavova.spacedout

import com.kurnavova.spacedout.data.spaceflights.network.SpaceFlightApi
import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests for [SpaceFlightApi].
 */
@ExperimentalCoroutinesApi
class SpaceFlightApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SpaceFlightApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(SpaceFlightApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    /**
     * Tests fetching a single article.
     */
    @Test
    fun getArticleTest() {
        runTest {
            val mockArticle = MockArticle()
            val mockResponse = getMockArticleResponse(mockArticle)

            mockWebServer.enqueue(
                MockResponse()
                    .setBody(mockResponse)
                    .setResponseCode(200)
            )

            val response = api.getArticle(1)

            assertTrue(response.isSuccessful)

            val body = response.body()

            assertNotNull(body)

            compareArticleWithResponse(
                mockArticle,
                body
            )
        }
    }

    /**
     * Tests fetching a single article with an error response.
     */
    @Test
    fun getArticleErrorTest() = runTest {
        val errorCode = 403
        mockWebServer.enqueue(MockResponse().setResponseCode(errorCode))

        val response = api.getArticle(1)

        assertFalse(response.isSuccessful)
        assertEquals(response.code(), errorCode)
    }

    /**
     * Tests fetching a paged list of articles.
     */
    @Test
    fun getPagedArticlesTest() = runTest {
        val mockArticles = listOf(
            MockArticle(id = 1),
            MockArticle(id = 2),
            MockArticle(id = 3),
            MockArticle(id = 4)
        )

        val next = "https://example.com/api/v1/articles/?offset=10&limit=10"
        val previous = "https://example.com/api/v1/articles/?offset=0&limit=10"

        val mockResponse = getMockPagedArticlesResponse(
            articles = mockArticles,
            next = next,
            previous = previous
        )

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val response = api.getPagedArticles(offset = 0, limit = 10)

        assertTrue(response.isSuccessful)

        val body = response.body()
        assertNotNull(body)

        assertEquals(next, body.next)
        assertEquals(previous, body.previous)

        assertEquals(mockArticles.size, body.results.size)
        mockArticles.forEach {
            val mockArticle = it
            val responseArticle = body.results.find { it.id == mockArticle.id }
            assertNotNull(responseArticle)
            compareArticleWithResponse(mockArticle, responseArticle)
        }

        // No ids are repeated in the list
        assertEquals(mockArticles.size, body.results.distinctBy { it.id }.size)
    }

    /**
     * Tests fetching a paged list of articles with an error response.
     */
    @Test
    fun getPagedArticlesErrorTest() = runTest {
        val errorCode = 404
        mockWebServer.enqueue(MockResponse().setResponseCode(errorCode))

        val response = api.getPagedArticles(0, 10)

        assertFalse(response.isSuccessful)
        assertEquals(response.code(), errorCode)
    }

    private fun compareArticleWithResponse(
        mockArticle: MockArticle,
        response: ArticleResponse
    ) {
        assertEquals(mockArticle.id, response.id)
        assertEquals(mockArticle.title, response.title)
        assertEquals(mockArticle.summary, response.summary)
        assertEquals(mockArticle.url, response.url)
        assertEquals(mockArticle.imageUrl, response.imageUrl)
        assertEquals(mockArticle.authors.size, response.authors.size)
        mockArticle.authors.forEach {
            assertTrue(response.authors.any { author -> author.name == it })
        }
    }

    private fun getMockPagedArticlesResponse(
        articles: List<MockArticle>,
        next: String,
        previous: String
    ): String = """
        {
            "count": ${articles.size},
            "next": "$next",
            "previous": "$previous",
            "results": ${articles.joinToString(prefix = "[", postfix = "]") { getMockArticleResponse(it) }}
        }
    """.trimIndent()

    private fun getMockArticleResponse(article: MockArticle = MockArticle()): String = """
        {
            "id": ${article.id},
            "title": "${article.title}",
            "url": "${article.url}",
            "summary": "${article.summary}",
            "image_url": "${article.imageUrl}",
            "authors": ${article.authors.joinToString(prefix = "[", postfix = "]") { """{"name": "$it"}""" }},
            "published_at": "${article.publishedAt}"
        }
    """.trimIndent()

}

private data class MockArticle(
    val id: Int = 1,
    val title: String = "Breaking News",
    val summary: String = "SpaceX finally launches Elon to outer space",
    val url: String = "https://example.com/article1",
    val imageUrl: String = "https://example.com/image1.jpg",
    val authors: List<String> = listOf("John Doe", "Jane Doe"),
    val publishedAt: String = "2025-03-25T12:00:00Z"
)
