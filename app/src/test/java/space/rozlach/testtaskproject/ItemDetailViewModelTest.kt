package space.rozlach.testtaskproject

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.domain.use_case.get_item.GetItemUseCase
import space.rozlach.testtaskproject.presentation.items_detail.ItemDetailState
import space.rozlach.testtaskproject.presentation.items_detail.ItemDetailViewModel
import kotlin.test.assertEquals

class ItemDetailViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var getItemUseCase: GetItemUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: ItemDetailViewModel

    @Before
    fun setUp() {
        getItemUseCase = mock(GetItemUseCase::class.java)
        savedStateHandle = mock(SavedStateHandle::class.java)
        viewModel = ItemDetailViewModel(getItemUseCase, savedStateHandle)
    }

    @Test
    fun `test loading state`() {
        testScope.runBlockingTest {
            val popisk = "examplePopisk"
            `when`(savedStateHandle.get<String>(eq("popisk") ?: "")).thenReturn(popisk)
            `when`(getItemUseCase(anyOrNull(), anyInt())).thenReturn(flow {
                emit(Resource.Loading())
            })

            viewModel.getItem(popisk ?: "", 0)

            val expectedState = ItemDetailState(isLoading = true)
            assertEquals(expectedState, viewModel.state.value)
        }
    }

    @Test
    fun `test success state`() {
        testScope.runBlockingTest {
            val popisk = "examplePopisk"
            val item = ItemDetailDto("1", popisk).toItemDetail()
            `when`(savedStateHandle.get<String>("popisk")).thenReturn(popisk)
            `when`(getItemUseCase(anyOrNull(), anyInt())).thenReturn(flow {
                emit(Resource.Success(item))
            })

            viewModel.getItem(popisk, 0)

            val expectedState = ItemDetailState(items = item)
            assertEquals(expectedState, viewModel.state.value)
        }
    }

    @Test
    fun `test error state`() {
        testScope.runBlockingTest {
            val popisk = "examplePopisk"
            val errorMessage = "Failed to load item"
            `when`(savedStateHandle.get<String>("popisk")).thenReturn(popisk)
            `when`(getItemUseCase(anyOrNull(), anyInt())).thenReturn(flow {
                emit(Resource.Error(errorMessage))
            })

            viewModel.getItem(popisk, 0)

            val expectedState = ItemDetailState(error = errorMessage)
            assertEquals(expectedState, viewModel.state.value)
        }
    }
}
