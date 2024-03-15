package space.rozlach.testtaskproject.presentation.items_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.domain.use_case.get_items.GetItemsUseCase
import javax.inject.Inject

@HiltViewModel
class ItemsListViewModel @Inject constructor(private val getItemsUseCase: GetItemsUseCase) :
    ViewModel() {
    private val _state = mutableStateOf<ItemsListState>(
        ItemsListState()
    )
    val state: State<ItemsListState> = _state

    init {
        getItems()
    }

    private fun getItems() {
        getItemsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ItemsListState(items = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = ItemsListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value =
                        ItemsListState(error = result.message ?: "An unexpected error occured")
                }
            }
        }.launchIn(viewModelScope)
    }
}