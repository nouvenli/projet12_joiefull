package fr.quinquenaire.projet12joiefull.presentation.ui.screen

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import fr.quinquenaire.projet12joiefull.R
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme
import fr.quinquenaire.projet12joiefull.presentation.ui.components.FavoriteBadge
import fr.quinquenaire.projet12joiefull.presentation.ui.components.NameAndRate
import fr.quinquenaire.projet12joiefull.presentation.ui.components.PriceTag
import fr.quinquenaire.projet12joiefull.presentation.ui.components.RatingTag

/**
 * Detailed view of a catalog item.
 * Accessibility: Updated buttons and interactive elements to respect the 48dp touch target rule.
 */
@Composable
fun ItemsDetails(
    item: CatalogItems,
    onToggleFavorite: (Long) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onRate: (Float) -> Unit,
    onCommentItem: (String) -> Unit,
    onShare: (String, Double) -> Unit,
    commentDraft: String,
    onCommentChanged: (String) -> Unit
) {
    /*
     *focusrequester prevents the text field from activating upon opening when talkback active
     *screenHeight determines the screen size for the image.
     *scrollState enables scrolling.
     */
    val focusRequester = remember { FocusRequester() }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val scrollState = rememberScrollState()

    // Accessibility strings
    val favoriteActionLabel = if (item.isFavorite) {
        stringResource(R.string.remove_from_favorites)
    } else {
        stringResource(R.string.add_to_favorites)
    }
    val shareActionLabel = stringResource(R.string.share)

    LaunchedEffect(item.id) {
        focusRequester.requestFocus()
        scrollState.scrollTo(0)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        // --- 1. Top Section (Image and Actions) ---
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(MaterialTheme.shapes.small)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.test_image)
                    .error(R.drawable.test_image)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .heightIn(max = screenHeight * 0.50f)
                    .fillMaxWidth()
                    .semantics {
                        customActions = listOf(
                            CustomAccessibilityAction(shareActionLabel) {
                                onShare(item.name, item.price)
                                true
                            },
                            CustomAccessibilityAction(favoriteActionLabel) {
                                onToggleFavorite(item.id)
                                true
                            }
                        )
                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(48.dp)
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }

                Surface(
                    color = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(48.dp)
                        .clearAndSetSemantics { }
                ) {
                    IconButton(onClick = { onShare(item.name, item.price) }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = stringResource(R.string.share)
                        )
                    }
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
                    .clearAndSetSemantics { }
            )
        }

        // --- 2. Information Block (Name, Rate, Price) ---
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .focusRequester(focusRequester)
                .focusable()
                .semantics(mergeDescendants = true) {
                    heading()
                }
        ) {
            NameAndRate(name = item.name, userRating = item.userRating)

            PriceTag(
                price = item.price,
                originalPrice = item.originalPrice,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // --- 3. Product Description ---
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        // --- 4. Rating Section ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.avatar),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            RatingTag(
                modifier = Modifier.semantics { traversalIndex = 6f },
                currentRating = item.userRating ?: 0f,
                onRatingChanged = { rating ->
                    onRate(rating)
                }
            )
        }

        // --- 5. Comment Section ---

        OutlinedTextField(
            value = commentDraft,
            onValueChange = onCommentChanged,
            label = { Text(stringResource(R.string.comment_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics { traversalIndex = 7f },
            shape = MaterialTheme.shapes.medium,
            maxLines = 3
        )

        Button(
            onClick = { onCommentItem(commentDraft) },
            modifier = Modifier
                .semantics { traversalIndex = 8f }
                .padding(top = 12.dp, bottom = 24.dp)
                .fillMaxWidth()
                .heightIn(min = 48.dp), // Accessibility: Ensure minimum touch target height
            enabled = commentDraft.isNotBlank() && commentDraft != (item.userComment ?: "")
        ) {
            Text(stringResource(R.string.send))
        }
    }
}
@Preview(showBackground = true, name = "Petit écran", device = "spec:width=320dp,height=480dp,dpi=320")
@Preview(showBackground = true, name = "Standard", device = "spec:width=411dp,height=891dp,dpi=420")
@Preview(showBackground = true, name = "Tablette", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(showBackground = true, fontScale = 1f)
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
                onBack = {},
                onRate = { _ -> },
                onCommentItem = { _ -> },
                onShare = { _, _ -> },
                commentDraft = "",
                onCommentChanged = {}
            )
        }
    }
}
