package space.rozlach.testtaskproject

import com.google.firebase.FirebaseException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.domain.model.ItemDetail
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import space.rozlach.testtaskproject.domain.use_case.get_item.GetItemUseCase
import java.io.IOException
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Assert.assertTrue

class GetItemUseCaseTest {

    private val repository: ItemRepository = mockk()
    private val getItemUseCase = GetItemUseCase(repository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test success scenario`() = runBlockingTest {
        // Mock repository response
        val mockItemDetailDto = ItemDetailDto("itemId", "itemName", "itemDescription")
        val mockItemDetail =
            mockItemDetailDto.toItemDetail() // Assuming you have a conversion method
        coEvery { repository.getItemDetail(any()) } returns mockItemDetailDto

        // Invoke the use case
        val result = mutableListOf<Resource<ItemDetail>>()
        getItemUseCase("itemId").collect {
            result.add(it)
        }

        // Check the result
        assertEquals(1, result.size) // Loading + Success
//        val loadingResult = result[0]
//        assertTrue(loadingResult is Resource.Loading)
        val successResult = result[0]
        assertTrue(successResult is Resource.Success)
        assertEquals(mockItemDetail, successResult.data)    }

    @Test
    fun `test FirebaseException scenario`() = runBlocking {
            // Mock repository to throw FirebaseException
            val errorMessage = "Firebase exception message"
            coEvery { repository.getItemDetail(any()) } throws FirebaseException(errorMessage)

            // Invoke the use case
            val result = mutableListOf<Resource<ItemDetail>>()
            getItemUseCase("itemId").collect { result.add(it) }

            // Check the result
            assertEquals(1, result.size)
            assertTrue(result[0] is Resource.Error)
            assertEquals(errorMessage, (result[0] as Resource.Error).message)
        }


    @Test
    fun `test IOException scenario`() = runBlocking {
            // Mock repository to throw FirebaseException
            val errorMessage = "Couldn't reach server. Check your internet connection."
            coEvery { repository.getItemDetail(any()) } throws IOException(errorMessage)

            // Invoke the use case
            val result = mutableListOf<Resource<ItemDetail>>()
            getItemUseCase("itemId").collect { result.add(it) }

            // Check the result
            assertEquals(1, result.size)
            assertTrue(result[0] is Resource.Error)
            assertEquals(errorMessage, (result[0] as Resource.Error).message)
        }
    }
