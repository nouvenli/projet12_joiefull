package fr.quinquenaire.projet12joiefull.domain.repository

import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import kotlinx.coroutines.flow.Flow

interface CatalogItemsRepository {
    fun getCatalogItemsList(): Flow<List<CatalogItems>>
    fun getCatalogItemsById(id:Long) : Flow<CatalogItems>

    // -- user actions --
    suspend fun updateUserRating(id: Long, rating: Float)
    suspend fun toggleFavorite(id: Long)

    // -- Json if room is empty --
    suspend fun ensureDataAvailable()
}
