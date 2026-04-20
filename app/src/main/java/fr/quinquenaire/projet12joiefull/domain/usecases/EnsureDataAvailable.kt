package fr.quinquenaire.projet12joiefull.domain.usecases

import fr.quinquenaire.projet12joiefull.domain.repository.CatalogItemsRepository
import javax.inject.Inject

class EnsureDataAvailable @Inject constructor(
    private val repository: CatalogItemsRepository
) {
    suspend operator fun invoke() {
        repository.ensureDataAvailable()
    }
}