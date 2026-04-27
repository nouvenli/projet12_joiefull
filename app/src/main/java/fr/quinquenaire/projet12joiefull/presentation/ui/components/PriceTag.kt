package fr.quinquenaire.projet12joiefull.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme

/**
 * Component to display the current price and the original price (if different).
 * Includes accessibility support for screen readers.
 */
@Composable
fun PriceTag(
    price: Double,
    originalPrice: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Current price (Joiefull price)
        Text(
            text = "${price}€",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.semantics {
                contentDescription = "prix joiefull : ${price}€"
            }
        )

        // Original price (Standard price)
        if (originalPrice > price) {
            Text(
                text = "${originalPrice}€",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = TextDecoration.LineThrough,
                modifier = Modifier.semantics {
                    contentDescription = "prix standard : ${originalPrice}€"
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PriceTagPreview() {
    JoiefullTheme {
        PriceTag(
            price = 49.99,
            originalPrice = 59.99
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PriceTagNoDiscountPreview() {
    JoiefullTheme {
        PriceTag(
            price = 39.99,
            originalPrice = 39.99
        )
    }
}
