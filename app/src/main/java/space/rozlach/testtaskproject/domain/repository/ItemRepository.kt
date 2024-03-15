package space.rozlach.testtaskproject.domain.repository

import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.data.remote.dto.ItemDto

interface ItemRepository {
    suspend fun getItemsList():List<ItemDto>
    suspend fun getItemDetail(popisk: String, position: Int): ItemDetailDto?
}