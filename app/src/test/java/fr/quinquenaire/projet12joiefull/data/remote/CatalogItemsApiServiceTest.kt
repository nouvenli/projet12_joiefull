package fr.quinquenaire.projet12joiefull.data.remote

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class CatalogItemsApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: CatalogItemsApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(CatalogItemsApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCatalogItemsList returns items correctly when response is 200`() = runTest {
        // GIVEN: Une réponse JSON simulée
        val mockJsonResponse = """
            [
              {
                "id": 1,
                "picture": {
                  "url": "https://example.com/image.jpg",
                  "description": "A nice shirt"
                },
                "name": "Shirt",
                "category": "Top",
                "likes": 10,
                "price": 29.99,
                "original_price": 39.99
              }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJsonResponse)
        )

        // WHEN: On appelle l'API
        val result = apiService.getCatalogItemsList()

        // THEN: On vérifie les données reçues
        assertEquals(1, result.size)
        val item = result[0]
        assertEquals(1L, item.id)
        assertEquals("Shirt", item.name)
        assertEquals("https://example.com/image.jpg", item.picture.url)
        assertEquals(29.99, item.price, 0.0)
    }
}
