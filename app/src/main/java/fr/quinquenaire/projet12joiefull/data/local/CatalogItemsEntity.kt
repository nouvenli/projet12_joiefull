package fr.quinquenaire.projet12joiefull.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity - local storage in SQLite.
 * Contains API fields + local user fields
 * (isFavorite, userRating) that do not exist in the JSON.
 */
@Entity(tableName = "catalog")
data class CatalogItemsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "originalPrice") val originalPrice: Double,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "userRating") val userRating: Float? = null
)