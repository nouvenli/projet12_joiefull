package fr.quinquenaire.projet12joiefull.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme

/**
 * card for horizontal list of items
 * Stateless : recieve catalogItems, 2 click with callbacks,
 * State in parent (CatalogItemsApp)
 *
 */

@Composable
fun ItemsCard(
    item: CatalogItems,
    onToggleFavorite: (Long) -> Unit, // a verifier onFavoriteClick: () -> Unit,
    onUpdateRating: (Long, Float) -> Unit,  // a verifier ?? pas pris
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
                .aspectRatio(3f / 4f) //paysage (16f / 9f)
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
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                color = Color.White.copy(alpha = 1f),
                contentColor = Color.Black,
                shape = MaterialTheme.shapes.extraLarge
                //border = BorderStroke(1.dp, Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )
                {
                    IconButton(
                        onClick = { onToggleFavorite(item.id) }
                    ) {
                        Icon(
                            imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (item.isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (item.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        "${item.likes}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                if (item.userRating != null) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${item.userRating}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                    Text(
                        text = "${item.price}€",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${item.originalPrice}€",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
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
                userRating = 4.8f
            ),
            onToggleFavorite = {},
            onUpdateRating = { _, _ -> },
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
                userRating = 4.2f
            ),
            onToggleFavorite = {},
            onUpdateRating = { _, _ -> },
            isSelected = true,
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
