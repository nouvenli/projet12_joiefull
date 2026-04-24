package fr.quinquenaire.projet12joiefull.presentation.ui

import android.content.Intent
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.quinquenaire.projet12joiefull.R
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme
import fr.quinquenaire.projet12joiefull.presentation.ui.screen.ItemsDetails
import fr.quinquenaire.projet12joiefull.presentation.ui.screen.ItemsList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Composant Stateful (gère l'état du ViewModel et de la navigation).
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CatalogItemsApp(
    windowSizeClass: WindowSizeClass,
    viewModel: CatalogItemsViewModel = hiltViewModel()
) {
    // 1. État des données
    val catalogUiState by viewModel.catalogUiState.collectAsStateWithLifecycle()

    // 2. État de navigation (Stateful)
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val scope = rememberCoroutineScope()

    // On passe tout au contenu Stateless
    CatalogItemsAppContent(
        catalogUiState = catalogUiState,
        navigator = navigator,
        scope = scope,
        onToggleFavorite = { viewModel.onToggleFavorite(it) },
        onUpdateRating = { id, rating -> viewModel.onUpdateRating(id, rating) },
        onCommentItem = { id, comment -> viewModel.onCommentItem(id, comment) }
    )
}

/**
 * Composant Stateless (reçoit l'état par paramètres).
 * Public pour permettre les tests d'interface.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CatalogItemsAppContent(
    catalogUiState: CatalogUiState,
    navigator: ThreePaneScaffoldNavigator<Long>,
    scope: CoroutineScope,
    onToggleFavorite: (Long) -> Unit,
    onUpdateRating: (Long, Float) -> Unit,
    onCommentItem: (Long, String) -> Unit
) {
    val context = LocalContext.current

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                ItemsList(
                    categories = catalogUiState.categories,
                    onItemClick = { itemId ->
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
            val selectedId = navigator.currentDestination?.contentKey
            val selectedItem = catalogUiState.categories
                .flatMap { it.items }
                .find { it.id == selectedId }

            AnimatedPane {
                if (selectedItem != null) {
                    ItemsDetails(
                        item = selectedItem,
                        onBack = {
                            scope.launch {
                                navigator.navigateBack()
                            }
                        },
                        onShare = { name, price ->
                            shareItem(context, name, price)
                        },
                        onRate = { rating ->
                            onUpdateRating(selectedItem.id, rating)
                        },
                        onToggleFavorite = {
                            onToggleFavorite(selectedItem.id)
                        },
                        onCommentItem = { comment ->
                            onCommentItem(selectedItem.id, comment)
                        }
                    )
                } else {
                    EmptyDetailPlaceholder()
                }
            }
        }
    )
}

private fun shareItem(context: android.content.Context, name: String, price: Double) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            context.getString(R.string.share_message, name, price)
        )
        type = "text/plain"
    }
    context.startActivity(
        Intent.createChooser(shareIntent, context.getString(R.string.share_chooser_title))
    )
}

@Composable
private fun EmptyDetailPlaceholder() {
    androidx.compose.material3.Text(stringResource(R.string.empty_detail_placeholder))
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,dpi=420")
@Composable
private fun CatalogItemsAppPreview() {
    val sampleItems = listOf(
        CatalogItems(
            id = 1L,
            name = "Veste en jean",
            imageUrl = "https://picsum.photos/200",
            description = "Une belle veste en jean indémodable.",
            category = "Hauts",
            likes = 10,
            price = 49.99,
            originalPrice = 59.99,
            isFavorite = false,
            userRating = 4.5f,
            userComment = "Super veste !"
        )
    )

    val categories = sampleItems.groupBy { it.category }
        .map { (name, items) -> CategorySection(name, items) }

    JoiefullTheme {
        // Dans la preview, on recrée un navigator et un scope "locaux"
        val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
        val scope = rememberCoroutineScope()

        CatalogItemsAppContent(
            catalogUiState = CatalogUiState(
                categories = categories
            ),
            navigator = navigator,
            scope = scope,
            onToggleFavorite = {},
            onUpdateRating = { _, _ -> },
            onCommentItem = { _, _ -> }
        )
    }
}
