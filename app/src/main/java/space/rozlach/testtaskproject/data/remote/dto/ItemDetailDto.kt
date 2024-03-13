package space.rozlach.testtaskproject.data.remote.dto

import space.rozlach.testtaskproject.domain.model.ItemDetail

data class ItemDetailDto(
    val popisk: String = "",
    val schkz: String = "",
    val schkz_text: String = "",
    val typk: String = "",
    val begda: String = "",
    val endda: String = "",
    val exported: String = "",
    val filename: String = ""
) {
    fun toItemDetail(): ItemDetail {
        return ItemDetail(
            popisk = popisk,
            schkz = schkz,
            schkz_text = schkz_text,
            typk = typk,
            begda = begda,
            endda = endda,
            exported = exported,
            filename = filename
        )
    }
}
