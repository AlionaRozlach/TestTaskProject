package space.rozlach.testtaskproject.data.remote.dto

import space.rozlach.testtaskproject.domain.model.Item

data class ItemDto(
    val popisk: String = "",
    val schkz: String = "",
    val schkz_text: String = "",
    val typk: String = "",
    val begda: String = "",
    val endda: String = "",
    val exported: String = "",
    val filename: String = ""
) {
    fun toItem(): Item {
        return Item(
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