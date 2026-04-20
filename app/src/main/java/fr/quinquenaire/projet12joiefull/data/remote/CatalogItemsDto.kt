package fr.quinquenaire.projet12joiefull.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network model - exactly reflects the structure of the API's JSON.
 * Used only by Retrofit for deserialization.
 * Picture is complex : url and description
 * @see <a href="https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/api/clothes.json">API clothes.json</a>
 */
@Serializable
data class CatalogItemsDto(
    @SerialName("id") val id: Long,
    @SerialName("picture") val picture: PictureDto,
    @SerialName("name") val name: String,
    @SerialName("category") val category: String,
    @SerialName("likes") val likes: Int,
    @SerialName("price") val price: Double,
    @SerialName("original_price") val originalPrice: Double
)

@Serializable
data class PictureDto(
    @SerialName("url") val url: String,
    @SerialName("description") val description: String
)