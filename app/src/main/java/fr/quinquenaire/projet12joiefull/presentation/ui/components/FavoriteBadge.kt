package fr.quinquenaire.projet12joiefull.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.quinquenaire.projet12joiefull.R
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme

@Composable
fun FavoriteBadge(
    itemId: Long,
    likes: Int,
    isFavorite: Boolean,
    onToggleFavorite: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White.copy(alpha = 1f),
        contentColor = Color.Black,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            IconButton(
                onClick = { onToggleFavorite(itemId) }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) {
                        stringResource(R.string.remove_from_favorites)
                    } else {
                        stringResource(R.string.add_to_favorites)
                    },
                    modifier = Modifier.size(16.dp),
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "$likes",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteBadgePreview() {
    JoiefullTheme {
        FavoriteBadge(
            itemId = 1L,
            likes = 42,
            isFavorite = false,
            onToggleFavorite = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteBadgeFavoritePreview() {
    JoiefullTheme {
        FavoriteBadge(
            itemId = 1L,
            likes = 43,
            isFavorite = true,
            onToggleFavorite = {}
        )
    }
}
