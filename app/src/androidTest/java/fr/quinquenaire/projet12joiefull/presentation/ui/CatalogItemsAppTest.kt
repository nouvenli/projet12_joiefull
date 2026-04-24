package fr.quinquenaire.projet12joiefull.presentation.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.rememberCoroutineScope
import org.junit.Rule
import org.junit.Test

class CatalogItemsAppTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Test
    fun itemsList_displaysCategoriesAndItems() {
        // GIVEN: Un état d'interface avec des données
        val sampleItems = listOf(
            CatalogItems(
                id = 1L,
                name = "Veste en jean",
                imageUrl = "",
                description = "Une belle veste",
                category = "Hauts",
                likes = 10,
                price = 49.99,
                originalPrice = 59.99,
                isFavorite = false,
                userRating = null,
                userComment = null
            )
        )
        val catalogUiState = CatalogUiState(
            categories = sampleItems.groupBy { it.category }
                .map { (name, items) -> CategorySection(name, items) }
        )

        // WHEN: On affiche l'application
        composeTestRule.setContent {
            JoiefullTheme {
                val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
                val scope = rememberCoroutineScope()
                CatalogItemsAppContent(
                    catalogUiState = catalogUiState,
                    navigator = navigator,
                    scope = scope,
                    onToggleFavorite = {},
                    onUpdateRating = { _, _ -> },
                    onCommentItem = { _, _ -> }
                )
            }
        }

        // THEN: La catégorie et le nom de l'item sont visibles
        composeTestRule.onNodeWithText("Hauts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Veste en jean").assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Test
    fun clickingItem_showsDetails() {
        // GIVEN
        val item = CatalogItems(
            id = 1L,
            name = "Veste en jean",
            imageUrl = "",
            description = "Une belle veste",
            category = "Hauts",
            likes = 10,
            price = 49.99,
            originalPrice = 59.99,
            isFavorite = false,
            userRating = null,
            userComment = null
        )
        val catalogUiState = CatalogUiState(
            categories = listOf(CategorySection("Hauts", listOf(item)))
        )

        composeTestRule.setContent {
            JoiefullTheme {
                val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
                val scope = rememberCoroutineScope()
                CatalogItemsAppContent(
                    catalogUiState = catalogUiState,
                    navigator = navigator,
                    scope = scope,
                    onToggleFavorite = {},
                    onUpdateRating = { _, _ -> },
                    onCommentItem = { _, _ -> }
                )
            }
        }

        // WHEN: On clique sur l'item
        composeTestRule.onNodeWithText("Veste en jean").performClick()

        // THEN: La description (qui est dans l'écran de détails) doit apparaître
        // Note: Sur une tablette, les deux sont affichés. Sur un téléphone, on navigue vers le détail.
        composeTestRule.onNodeWithText("Une belle veste").assertIsDisplayed()
    }
}
