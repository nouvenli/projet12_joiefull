package fr.quinquenaire.projet12joiefull.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme
import fr.quinquenaire.projet12joiefull.presentation.ui.components.FavoriteBadge
import fr.quinquenaire.projet12joiefull.presentation.ui.components.NameAndRate
import fr.quinquenaire.projet12joiefull.presentation.ui.components.PriceTag

/**
 * card for horizontal list of items
 * Stateless : recieve catalogItems, 2 click with callbacks,
 * State in parent (CatalogItemsApp)
 *
 */

@Composable
fun ItemsCard(
    item: CatalogItems,
    onToggleFavorite: (Long) -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        elevation = CardDefaults.elevatedCardElevation(2.dp) // a verifier elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.description,
                contentScale = ContentScale.Crop
            )

            FavoriteBadge(
                itemId = item.id,
                likes = item.likes,
                isFavorite = item.isFavorite,
                onToggleFavorite = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            )
        }


        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            NameAndRate(name = item.name, userRating = item.userRating)

            PriceTag(
                price = item.price,
                originalPrice = item.originalPrice,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ItemsCardPreview() {
    JoiefullTheme {
        ItemsCard(
            item = CatalogItems(
                id = 1,
                name = "Sac à main élégant",
                imageUrl = "https://example.com/bag.jpg",
                description = "Un sac à main en cuir de haute qualité",
                category = "Accessoires",
                likes = 45,
                price = 120.0,
                originalPrice = 150.0,
                isFavorite = false,
                userRating = 4.8f,
                userComment = "Très confortable !"
            ),
            onToggleFavorite = {},
            isSelected = false,
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemsCardFavoriteSelectedPreview() {
    JoiefullTheme {
        ItemsCard(
            item = CatalogItems(
                id = 2,
                name = "Robe de soirée",
                imageUrl = "https://example.com/dress.jpg",
                description = "Une robe de soirée rouge éclatante",
                category = "Vêtements",
                likes = 120,
                price = 85.0,
                originalPrice = 100.0,
                isFavorite = true,
                userRating = 4.2f,
                userComment = "Très belle robe !"
            ),
            onToggleFavorite = {},
            isSelected = true,
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
