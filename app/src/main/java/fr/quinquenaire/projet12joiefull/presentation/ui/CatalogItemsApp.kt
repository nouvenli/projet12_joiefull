package fr.quinquenaire.projet12joiefull.presentation.ui

import android.content.Intent
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme
import fr.quinquenaire.projet12joiefull.presentation.ui.screen.ItemsDetails
import fr.quinquenaire.projet12joiefull.presentation.ui.screen.ItemsList
import kotlinx.coroutines.launch


@Composable
fun CatalogItemsApp(
    windowSizeClass: WindowSizeClass,
    viewModel: CatalogItemsViewModel = hiltViewModel()
) {
    // 1. Récupération des données
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatalogItemsAppContent(
        uiState = uiState,
        onToggleFavorite = { viewModel.onToggleFavorite(it) },
        onUpdateRating = { id, rating -> viewModel.onUpdateRating(id, rating) },
        onCommentItem = { id, comment -> viewModel.onCommentItem(id, comment) }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CatalogItemsAppContent(
    uiState: UiState,
    onToggleFavorite: (Long) -> Unit,
    onUpdateRating: (Long, Float) -> Unit,
    onCommentItem: (Long, String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()  //pour fonction suspendue navigateTo

    // 2. Initialisation du navigateur adaptatif (on transporte l'ID de l'article)
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()


    // 3. Le Scaffold Adaptatif
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            // -- liste --
            AnimatedPane {
                ItemsList(
                    itemsByCategory = uiState.catalogItemsByCategory,
                    onItemClick = { itemId ->
                        // navigation dans coroutines
                        scope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, itemId)
                        }
                    },
                    onToggleFavorite = onToggleFavorite,
                    selectedItemId = navigator.currentDestination?.contentKey
                )
            }
        },
        detailPane = {
            // -- details --
            val selectedId = navigator.currentDestination?.contentKey

            // On cherche l'item correspondant à l'ID sélectionné
            val selectedItem = uiState.catalogItemsByCategory.values
                .flatten()
                .find { it.id == selectedId }

            AnimatedPane {
                if (selectedItem != null) {
                    ItemsDetails(
                        item = selectedItem,
                        onBack = {
                            // Gère le retour sur téléphone
                            scope.launch {
                                navigator.navigateBack()
                            }
                        },
                        onShare = { name:String, price: Double ->
                            shareItem(context, name, price)
                        },
                        onRate = { rating: Float ->
                            onUpdateRating(selectedItem.id, rating)
                        },
                        onToggleFavorite = {
                            onToggleFavorite(selectedItem.id)
                        },
                        onCommentItem = { comment: String ->
                            onCommentItem(selectedItem.id, comment)
                        }
                    )
                } else {
                    // Optionnel : un écran vide ou un message "Sélectionnez un article"
                    EmptyDetailPlaceholder()
                }
            }
        }
    )
}

fun shareItem(context: android.content.Context, name: String, price: Double) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "Regarde cet article sur Joiefull : $name à ${price}€ !"
        )
        type = "text/plain"
    }
    context.startActivity(
        Intent.createChooser(shareIntent, "Partager via")
    )
}

@Composable
fun EmptyDetailPlaceholder() {
    // Implémentation simple
    androidx.compose.material3.Text("Sélectionnez un article")
}

/*@Preview(showBackground = true)
@Composable
fun CatalogItemsAppPreview() {
    val sampleItems = listOf(
        CatalogItems(
            id = 1L,
            name = "Veste en jean",
            imageUrl = "",
            description = "Une belle veste en jean",
            category = "Hauts",
            likes = 10,
            price = 49.99,
            originalPrice = 59.99,
            isFavorite = false,
            userRating = 4.5f,
            userComment = "Super veste !"
        ),
        CatalogItems(
            id = 2L,
            name = "Pantalon chino",
            imageUrl = "",
            description = "Pantalon chino confortable",
            category = "Bas",
            likes = 5,
            price = 39.99,
            originalPrice = 39.99,
            isFavorite = true,
            userRating = null,
            userComment = null
        )
    )

    JoiefullTheme {
        CatalogItemsAppContent(
            uiState = UiState(
                catalogItemsByCategory = sampleItems.groupBy { it.category }
            ),
            onToggleFavorite = {},
            onUpdateRating = { _, _ -> },
            onCommentItem = { _, _ -> }
        )
    }
}*/
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240") // Preview en mode Tablette
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,dpi=420")  // Preview en mode Téléphone
@Composable
fun CatalogItemsAppPreview() {
    val sampleItems = listOf(
        CatalogItems(
            id = 1L,
            name = "Veste en jean",
            imageUrl = "https://picsum.photos/200", // Image de test
            description = "Une belle veste en jean indémodable.",
            category = "Hauts",
            likes = 10,
            price = 49.99,
            originalPrice = 59.99,
            isFavorite = false,
            userRating = 4.5f,
            userComment = "Super veste !"
        ),
        CatalogItems(
            id = 2L,
            name = "Pantalon chino",
            imageUrl = "https://picsum.photos/201", // Image de test
            description = "Pantalon chino confortable pour l'été.",
            category = "Bas",
            likes = 5,
            price = 39.99,
            originalPrice = 39.99,
            isFavorite = true,
            userRating = null,
            userComment = null
        )
    )

    JoiefullTheme {
        CatalogItemsAppContent(
            uiState = UiState(
                catalogItemsByCategory = sampleItems.groupBy { it.category }
            ),
            onToggleFavorite = {},
            onUpdateRating = { _, _ -> },
            onCommentItem = { _, _ -> }
        )
    }
}