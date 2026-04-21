package fr.quinquenaire.projet12joiefull.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems

@Composable
fun ItemsList(
    modifier: Modifier = Modifier,
    itemsByCategory: Map<String, List<CatalogItems>>,
    onItemClick: (Long) -> Unit,
    selectedItemId: Long? = null,
    onToggleFavorite: (Long) -> Unit = {}
) {
    if (itemsByCategory.isEmpty()) {

        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Aucune donnée chargée. Vérifiez les logs.")
        }
    } else {
        LazyColumn(modifier = modifier) {
            itemsByCategory.forEach { (category, items) ->
                item {
                    Text(text = category, style = MaterialTheme.typography.titleMedium)
                }
                item {
                    LazyRow {
                        items(items) { item ->
                            ItemsCard(
                                item = item,
                                onClick = { onItemClick(item.id) },
                                isSelected = item.id == selectedItemId,
                                onToggleFavorite = onToggleFavorite,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}