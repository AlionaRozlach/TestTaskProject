package space.rozlach.testtaskproject.domain.repository

import androidx.lifecycle.LiveData
import space.rozlach.testtaskproject.data.remote.dto.ItemDto

interface ItemRepository {
    suspend fun getItemsList(): LiveData<List<ItemDto>>
}