package fr.quinquenaire.projet12joiefull.data.repository

import fr.quinquenaire.projet12joiefull.data.local.CatalogItemsDao
import fr.quinquenaire.projet12joiefull.data.mapper.toDomain
import fr.quinquenaire.projet12joiefull.data.mapper.toEntity
import fr.quinquenaire.projet12joiefull.data.remote.CatalogItemsApiService
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.domain.repository.CatalogItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of the [CatalogItemsRepository] interface.
 *
 * This class acts as the single source of truth for catalog data, coordinating between the
 * local database ([CatalogItemsDao]) and the remote data source ([CatalogItemsApiService]).
 * It handles data mapping between data layer entities and domain layer models.
 *
 * @property catalogItemsDao The Data Access Object for local database operations.
 * @property catalogItemsApiService The API service for fetching data from the remote server.
 */
class RepositoryImpl @Inject constructor(
    private val catalogItemsDao: CatalogItemsDao,
    private val catalogItemsApiService: CatalogItemsApiService
) : CatalogItemsRepository {

    // -- read --
    override fun getCatalogItemsList(): Flow<List<CatalogItems>> {
        return catalogItemsDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCatalogItemsById(id: Long): Flow<CatalogItems> {
        return catalogItemsDao.getById(id).map { it.toDomain() }
    }

    // -- populate  --
    override suspend fun ensureDataAvailable() {
        if (catalogItemsDao.getCount() == 0) {
            val catalogItems = catalogItemsApiService.getCatalogItemsList()
            catalogItemsDao.insertAll(catalogItems.map { it.toEntity() })
        }
    }

    // -- user actions --
    override suspend fun updateUserRating(id: Long, rating: Float) {
        catalogItemsDao.updateUserRating(id, rating)
    }

    override suspend fun toggleFavorite(id: Long) {
        catalogItemsDao.toggleFavorite(id)
    }

    override suspend fun updateUserComment(id: Long, comment: String) {
        catalogItemsDao.updateUserComment(id, comment)
    }
}
