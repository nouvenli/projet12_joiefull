package fr.quinquenaire.projet12joiefull.data.mapper

import fr.quinquenaire.projet12joiefull.data.local.CatalogItemsEntity
import fr.quinquenaire.projet12joiefull.domain.model.CatalogItems

fun CatalogItemsEntity.toDomain(): CatalogItems {
    return CatalogItems(
        id = id,
        name = name,
        imageUrl = imageUrl,
        description = description,
        category = category,
        likes = likes,
        price = price,
        originalPrice = originalPrice,
        isFavorite = isFavorite,
        userRating = userRating
    )
}