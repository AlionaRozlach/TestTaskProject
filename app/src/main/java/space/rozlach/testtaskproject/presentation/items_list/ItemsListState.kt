package space.rozlach.testtaskproject.presentation.items_list

import space.rozlach.testtaskproject.domain.model.Item


data class ItemsListState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val error: String = ""
)