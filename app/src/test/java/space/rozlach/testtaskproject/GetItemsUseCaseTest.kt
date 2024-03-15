package space.rozlach.testtaskproject

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.domain.model.Item
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import space.rozlach.testtaskproject.domain.use_case.get_items.GetItemsUseCase
import java.io.IOException


class GetItemsUseCaseTest {
    private val mockRepository: ItemRepository = mockk()
    private val getItemsUseCase = GetItemsUseCase(mockRepository)

    @Test
    fun `emit Loading state then Success state when repository returns items`() = runBlocking {
        val items = listOf(
            ItemDto("1", "Item 1", "", "", "", "", "", ""),
            ItemDto("2", "Item 2", "", "", "", "", "", "")
        )
        coEvery { mockRepository.getItemsList() } returns items

        val mockItemsList = items.map { it.toItem() }

        // Call the use case and collect the emitted states
        val flow = getItemsUseCase()
        val states = flow.toList()

        // Verify emitted states
        assertEquals(1, states.size)
        val successResult = states[0]
        assertTrue(successResult is Resource.Success)
        assertEquals(mockItemsList, successResult.data)
    }

    @Test
    fun `emit Loading state then Error state when repository throws IOException`() = runBlocking {
        val errorMessage = "Couldn't reach server. Check your internet connection."

        // Mock repository to throw IOException
        coEvery { mockRepository.getItemsList() } throws IOException(errorMessage)

        // Call the use case and collect the emitted states
        val flow = getItemsUseCase()
        val states = flow.toList()

        // Check the result
        assertEquals(1, states.size)
        assertTrue(states[0] is Resource.Error)
        assertEquals(errorMessage, (states[0] as Resource.Error).message)
    }

    @Test
    fun `emit Loading state then Error state with default message when repository returns empty list`() =
        runBlocking {
            val errorMessage = "An unexpected error occurred"
            // Mock repository to return empty list
            coEvery { mockRepository.getItemsList() } returns emptyList()

            // Call the use case and collect the emitted states
            val flow = getItemsUseCase()
            val states = flow.toList()

//             Verify emitted states
            assertEquals(1, states.size)
            assertTrue(states[0] is Resource.Error)
            assertEquals(errorMessage, (states[0] as Resource.Error).message)
        }
}