package fr.quinquenaire.projet12joiefull.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import fr.quinquenaire.projet12joiefull.presentation.ui.components.RatingTag


@Composable
fun ItemsDetails(
    item: CatalogItems,
    onToggleFavorite: (Long) -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onRateClick: (Long, Float) -> Unit,
    onCommentItem: (Long, String) -> Unit,
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                }
                IconButton(onClick = { /* Action Partage */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Partager")
                }
            }

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
            modifier = Modifier.padding(top = 8.dp)
        ) {
            NameAndRate(name = item.name, userRating = item.userRating)

            PriceTag(
                price = item.price,
                originalPrice = item.originalPrice,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
//zone notation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                // AsyncImage ou Icon A VOIR
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Avatar",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            RatingTag(
                currentRating = item.userRating ?: 0f,
                onRatingChanged = { rating ->
                    onRateClick(item.id, rating)
                }
            )

        }
        var commentText by remember(item.id) {
            mutableStateOf(item.userComment ?: "")
        }

        OutlinedTextField(
            value = commentText,
            onValueChange = { newText ->
                commentText = newText
            },
            label = { Text("Partagez ici vos impressions sur cette pièce") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            shape = MaterialTheme.shapes.medium,
            maxLines = 3
        )

        // Save if comment
        Button(
            onClick = { onCommentItem(item.id, commentText) },
            modifier = Modifier.padding(top = 8.dp),

            enabled = commentText.isNotBlank() && commentText != (item.userComment ?: "")
        ) {
            Text("Envoyer")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemsDetailsPreview() {
    JoiefullTheme {
        Surface {
            ItemsDetails(
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
                    userRating = 4.5f,
                    userComment = "Très confortable !"
                ),
                onToggleFavorite = {},
                isSelected = false,
                onClick = {},
                onBack = {},
                onRateClick = { _, _ -> },
                onCommentItem = { _, _ -> }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemsDetailsFavoritePreview() {
    JoiefullTheme {
        Surface {
            ItemsDetails(
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
                    userComment = "Très belle robe, je recommande !"
                ),
                onToggleFavorite = {},
                isSelected = true,
                onClick = {},
                onBack = {},
                onRateClick = { _, _ -> },
                onCommentItem = { _, _ -> }
            )
        }
    }
}
