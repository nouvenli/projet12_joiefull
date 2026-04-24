package fr.quinquenaire.projet12joiefull.presentation.ui

import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.domain.usecases.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatalogItemsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    // Mocks des Use Cases
    private val getCatalogItemsList: GetCatalogItemsList = mockk()
    private val updateRating: UpdateRating = mockk()
    private val toggleFavorite: ToggleFavorite = mockk()
    private val getCatalogItemsById: GetCatalogItemsById = mockk()
    private val ensureDataAvailable: EnsureDataAvailable = mockk()
    private val commentItem: CommentItem = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialization loads data and groups by category`() = runTest {
        // GIVEN: Une liste de produits
        val items = listOf(
            createMockItem(1, "Top"),
            createMockItem(2, "Top"),
            createMockItem(3, "Bottom")
        )
        every { getCatalogItemsList() } returns flowOf(items)
        coEvery { ensureDataAvailable() } returns Unit

        // WHEN: On crée le ViewModel
        val viewModel = CatalogItemsViewModel(
            getCatalogItemsList, updateRating, toggleFavorite, getCatalogItemsById, ensureDataAvailable, commentItem
        )

        // On lance un collecteur pour activer le StateFlow
        val collectJob = launch { viewModel.catalogUiState.collect {} }
        advanceUntilIdle()

        // THEN: Les données sont groupées
        val state = viewModel.catalogUiState.value
        assertEquals(2, state.categories.size)
        assertEquals(2, state.categories.find { it.name == "Top" }?.items?.size)
        assertEquals(1, state.categories.find { it.name == "Bottom" }?.items?.size)
        
        collectJob.cancel()
    }

    @Test
    fun `loadInitialData sets error when ensureDataAvailable fails`() = runTest {
        // GIVEN
        val errorMessage = "Network Error"
        every { getCatalogItemsList() } returns flowOf(emptyList())
        coEvery { ensureDataAvailable() } throws Exception(errorMessage)

        // WHEN
        val viewModel = CatalogItemsViewModel(
            getCatalogItemsList, updateRating, toggleFavorite, getCatalogItemsById, ensureDataAvailable, commentItem
        )
        
        val collectJob = launch { viewModel.catalogUiState.collect {} }
        advanceUntilIdle()

        // THEN
        assertEquals(errorMessage, viewModel.catalogUiState.value.error)
        collectJob.cancel()
    }

    private fun createMockItem(id: Long, category: String) = CatalogItems(
        id = id,
        name = "Item $id",
        imageUrl = "",
        description = "",
        category = category,
        likes = 0,
        price = 10.0,
        originalPrice = 15.0,
        isFavorite = false,
        userRating = null,
        userComment = null
    )
}
