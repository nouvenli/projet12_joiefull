package fr.quinquenaire.projet12joiefull.presentation.ui

import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems

data class CategorySection(
    val name: String,
    val items: List<CatalogItems>
)

data class CatalogUiState (
    val categories: List<CategorySection> = emptyList(),
    val selectedItem: CatalogItems? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
