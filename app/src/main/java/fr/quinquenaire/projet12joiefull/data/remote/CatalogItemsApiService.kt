package fr.quinquenaire.projet12joiefull.data.remote

import retrofit2.http.GET

/**
 * Interface Retrofit for the catalog items API.
 * The base URL is configured in the Hilt module (NetworkModule)..
 */
interface CatalogItemsApiService {

    @GET("clothes.json")
    suspend fun getCatalogItemsList(): List<CatalogItemsDto>
}