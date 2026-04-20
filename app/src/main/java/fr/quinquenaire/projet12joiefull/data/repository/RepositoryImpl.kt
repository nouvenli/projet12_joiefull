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

    // -- populate if room is empty --
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
}