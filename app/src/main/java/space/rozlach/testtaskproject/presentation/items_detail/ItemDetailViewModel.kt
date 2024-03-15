package space.rozlach.testtaskproject.presentation.items_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.domain.use_case.get_item.GetItemUseCase
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = mutableStateOf(ItemDetailState())
    var state: State<ItemDetailState> = _state

    init {
        val popisk = savedStateHandle.get<String>("popisk")
        if (!popisk.isNullOrEmpty())
            getItem(popisk)
    }

    internal fun getItem(popisk: String) {
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