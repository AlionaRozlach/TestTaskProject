package space.rozlach.testtaskproject.presentation.items_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import space.rozlach.testtaskproject.core.Constants
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.domain.use_case.get_item.GetItemUseCase
import space.rozlach.testtaskproject.domain.use_case.get_items.GetItemsUseCase
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ItemDetailState())
    val state: State<ItemDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_ITEM_POPISK)?.let { popisk ->
            getItem(popisk)
        }
    }

    private fun getItem(popisk: String) {
        getItemUseCase(popisk).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ItemDetailState(items = result.data)
                }
                is Resource.Error -> {
                    _state.value = ItemDetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ItemDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}