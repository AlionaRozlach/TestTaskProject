package space.rozlach.testtaskproject.data.remote.dto

import space.rozlach.testtaskproject.domain.model.ItemDetail

data class ItemDetailDto(
    val popisk: String,
    val schkz: String,
    val schkzText: String,
    val typk: String,
    val begda: String,
    val endda: String,
    val exported: String,
    val filename: String
)

fun ItemDetailDto.toItemDetail(): ItemDetail {
    return ItemDetail(
        popisk = popisk,
        schkz = schkz,
        schkzText = schkzText,
        typk = typk,
        begda = begda,
        endda = endda,
        exported = exported,
        filename = filename
    )
}
