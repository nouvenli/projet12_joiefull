package fr.quinquenaire.projet12joiefull.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import fr.quinquenaire.projet12joiefull.R
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme

/**
 * Interactive rating component using stars.
 * Provides accessibility support by describing the rating action.
 */
@Composable
fun RatingTag(
    currentRating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.semantics(mergeDescendants = true) {
                // Provides a global description for the rating group
                contentDescription = "noter ${currentRating.toInt()} étoiles sur $maxRating"
            }
        ) {
            for (i in 1..maxRating) {
                val isSelected = i <= currentRating
                IconButton(
                    onClick = {
                        onRatingChanged(i.toFloat())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(R.string.rating_description, i, maxRating),
                        tint = if (isSelected) Color(0xFFFFC107) else Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingTagPreview() {
    JoiefullTheme {
        RatingTag(
            currentRating = 3f,
            onRatingChanged = {}
        )
    }
}
