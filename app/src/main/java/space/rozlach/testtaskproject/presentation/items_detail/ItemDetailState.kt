package space.rozlach.testtaskproject.presentation.items_detail
import space.rozlach.testtaskproject.domain.model.ItemDetail

data class ItemDetailState(
    val isLoading: Boolean = false,
    val items: ItemDetail? = null,
    val error: String = ""
)