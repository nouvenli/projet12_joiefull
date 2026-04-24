package fr.quinquenaire.projet12joiefull.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RatingTag(
    currentRating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    Column(modifier = modifier) {
        // avatar and rating
        Row(modifier = modifier) {
            for (i in 1..maxRating) {
                val isSelected = i <= currentRating
                IconButton(
                    onClick = {
                        onRatingChanged(i.toFloat())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Étoile $i",
                        tint = if (isSelected) Color(0xFFFFC107) else Color.Gray
                    )
                }
            }
        }
    }
}