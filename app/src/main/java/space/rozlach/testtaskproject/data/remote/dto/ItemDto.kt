package space.rozlach.testtaskproject.data.remote.dto

import space.rozlach.testtaskproject.domain.model.Item

//data class ItemDto(
//    val popisk: String
//)
//{
//    fun toItem(): Item {
//        return Item(
//            popisk = popisk,
////            schkz = schkz,
////            schkzText = schkzText,
////            typk = typk,
////            begda = begda,
////            endda = endda,
////            exported = exported,
////            filename = filename
//        )
//    }
//}


data class ItemDto(
    val popisk: String = ""
) {
    // Add a no-argument constructor if required by Firebase
    constructor() : this("")

    fun toItem(): Item {
        return Item(
            popisk = popisk
            // Uncomment the lines below if you want to include additional properties
            // schkz = schkz,
            // schkzText = schkzText,
            // typk = typk,
            // begda = begda,
            // endda = endda,
            // exported = exported,
            // filename = filename
        )
    }
}