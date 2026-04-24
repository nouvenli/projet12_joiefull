package fr.quinquenaire.projet12joiefull.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.quinquenaire.projet12joiefull.R
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme
import fr.quinquenaire.projet12joiefull.presentation.ui.CategorySection

@Composable
fun ItemsList(
    modifier: Modifier = Modifier,
    categories: List<CategorySection>,
    onItemClick: (Long) -> Unit,
    selectedItemId: Long? = null,
    onToggleFavorite: (Long) -> Unit = {}
) {
    if (categories.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_data_message))
        }
    } else {
        LazyColumn(modifier = modifier) {
            categories.forEach { categorySection ->
                item(
                    key = "${categorySection.name}_title",
                    contentType = "category_header"
                ) {
                    Text(
                        text = categorySection.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                item(
                    key = "${categorySection.name}_row",
                    contentType = "items_row"
                ) {
                    LazyRow {
                        items(
                            items = categorySection.items,
                            key = { it.id },
                            contentType = { "catalog_item" }
                        ) { item ->
                            ItemsCard(
                                item = item,
                                onClick = { onItemClick(item.id) },
                                isSelected = item.id == selectedItemId,
                                onToggleFavorite = onToggleFavorite,
                                modifier = Modifier
                                    .width(200.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemsListPreview() {
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
        ),
        CatalogItems(
            id = 2L,
            name = "Pantalon chino",
            imageUrl = "https://picsum.photos/201",
            description = "Un pantalon confortable pour toutes les occasions.",
            category = "Bas",
            likes = 5,
            price = 39.99,
            originalPrice = 39.99,
            isFavorite = true,
            userRating = 4.0f,
            userComment = "Bonne qualité."
        ),
        CatalogItems(
            id = 3L,
            name = "T-shirt coton",
            imageUrl = "https://picsum.photos/202",
            description = "Un t-shirt basique en coton bio.",
            category = "Hauts",
            likes = 20,
            price = 19.99,
            originalPrice = 24.99,
            isFavorite = false,
            userRating = 4.8f,
            userComment = "Très doux."
        )
    )

    val categories = sampleItems.groupBy { it.category }
        .map { (name, items) -> CategorySection(name, items) }

    JoiefullTheme {
        ItemsList(
            categories = categories,
            onItemClick = {},
            onToggleFavorite = {}
        )
    }
}
