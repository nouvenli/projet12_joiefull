package fr.quinquenaire.projet12joiefull.domain.usecases

import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems
import fr.quinquenaire.projet12joiefull.domain.repository.CatalogItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatalogItemsById  @Inject constructor(
    private val repository: CatalogItemsRepository
){
    fun invoke(id: Long): Flow<CatalogItems> {
        return repository.getCatalogItemsById(id)
    }
}