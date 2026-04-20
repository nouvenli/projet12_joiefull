package fr.quinquenaire.projet12joiefull.domain.model

data class CatalogItems (
    val id: Long,
    val name: String,
    val imageUrl: String,
    val description: String,
    val category: String,
    val likes: Int,
    val price: Double,
    val originalPrice: Double,
    val isFavorite: Boolean,
    val userRating: Float?
)