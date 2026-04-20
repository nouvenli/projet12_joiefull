package fr.quinquenaire.projet12joiefull.domain.usecases

import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.domain.repository.CatalogItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatalogItemsList @Inject constructor(
    private val repository: CatalogItemsRepository
){
    suspend operator fun invoke(): Flow<List<CatalogItems>> {
        return repository.getCatalogItemsList()
    }
}