package fr.quinquenaire.projet12joiefull.data.mapper

import fr.quinquenaire.projet12joiefull.data.local.CatalogItemsEntity
import fr.quinquenaire.projet12joiefull.data.remote.CatalogItemsDto


fun CatalogItemsDto.toEntity (): CatalogItemsEntity {
    return CatalogItemsEntity(
        id = id,
        name = name,
        imageUrl = picture.url,
        description = picture.description,
        category = category,
        likes = likes,
        price = price,
        originalPrice = originalPrice,
        isFavorite = false,
        userRating = null
    )
}