package fr.quinquenaire.projet12joiefull.data.repository

import fr.quinquenaire.projet12joiefull.data.local.CatalogItemsDao
import fr.quinquenaire.projet12joiefull.data.remote.CatalogItemsApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    private lateinit var dao: CatalogItemsDao
    private lateinit var apiService: CatalogItemsApiService
    private lateinit var repository: RepositoryImpl

    @Before
    fun setup() {
        dao = mockk(relaxed = true) // relaxed=true permet de ne pas mocker chaque appel void/unit
        apiService = mockk()
        repository = RepositoryImpl(dao, apiService)
    }

    @Test
    fun `ensureDataAvailable calls API and inserts when DB is empty`() = runTest {
        // GIVEN: Le DAO renvoie 0 (base vide)
        coEvery { dao.getCount() } returns 0
        coEvery { apiService.getCatalogItemsList() } returns emptyList()

        // WHEN: On demande de s'assurer que les données sont là
        repository.ensureDataAvailable()

        // THEN: L'API doit avoir été appelée et l'insertion faite
        coVerify(exactly = 1) { apiService.getCatalogItemsList() }
        coVerify(exactly = 1) { dao.insertAll(any()) }
    }

    @Test
    fun `ensureDataAvailable does NOT call API when DB is not empty`() = runTest {
        // GIVEN: Le DAO renvoie 10 (base déjà remplie)
        coEvery { dao.getCount() } returns 10

        // WHEN: On demande de s'assurer que les données sont là
        repository.ensureDataAvailable()

        // THEN: L'API ne doit JAMAIS être appelée
        coVerify(exactly = 0) { apiService.getCatalogItemsList() }
        coVerify(exactly = 0) { dao.insertAll(any()) }
    }
}
