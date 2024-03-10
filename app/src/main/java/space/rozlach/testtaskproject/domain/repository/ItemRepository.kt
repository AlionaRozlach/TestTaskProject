package space.rozlach.testtaskproject.domain.repository

import androidx.lifecycle.LiveData
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.domain.model.ItemDetail

interface ItemRepository {
    suspend fun getItemsList():List<ItemDto>
    suspend fun getItemDetail(popisk: String): ItemDetailDto?
}