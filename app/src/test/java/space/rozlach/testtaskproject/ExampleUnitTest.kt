package space.rozlach.testtaskproject

import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import kotlin.test.assertEquals
import com.google.firebase.database.ValueEventListener
import junit.framework.Assert
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import space.rozlach.testtaskproject.data.repository.ItemRepositoryImpl
import org.mockito.kotlin.argumentCaptor
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Mock
    private lateinit var firebaseDatabase: FirebaseDatabase

    @Mock
    private lateinit var databaseReference: DatabaseReference

    @Mock
    private lateinit var query: Query

    @Mock
    private lateinit var dataSnapshot: DataSnapshot

    @Captor
    private lateinit var valueEventListenerCaptor: ArgumentCaptor<ValueEventListener>

    private lateinit var itemRepository: ItemRepositoryImpl


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(firebaseDatabase.reference).thenReturn(databaseReference)
        `when`(firebaseDatabase.reference.orderByChild(any())).thenReturn(query)
        itemRepository = ItemRepositoryImpl(firebaseDatabase)
    }

    @After
    fun tearDown() {
        // No teardown needed for Mockito
    }

    @Test
    fun testGetItemsListSuccess() {
        val item1 = ItemDto("1", "popisk1")
        val item2 = ItemDto("2", "popisk2")
        val itemList = listOf(item1, item2)
        val dataSnapshotChild1 = mock(DataSnapshot::class.java)
        val dataSnapshotChild2 = mock(DataSnapshot::class.java)

        `when`(dataSnapshot.children).thenReturn(listOf(dataSnapshotChild1, dataSnapshotChild2))
        `when`(dataSnapshotChild1.getValue(ItemDto::class.java)).thenReturn(item1)
        `when`(dataSnapshotChild2.getValue(ItemDto::class.java)).thenReturn(item2)

        runBlocking {
            val listenerCaptor = argumentCaptor<ValueEventListener>()
            `when`(firebaseDatabase.reference.addListenerForSingleValueEvent(listenerCaptor.capture())).thenAnswer {
                listenerCaptor.firstValue.onDataChange(dataSnapshot)
            }

            val itemRepository = ItemRepositoryImpl(firebaseDatabase)
            val result = itemRepository.getItemsList()
            assertEquals(2, result.size)
            assertEquals(itemList[0].popisk, result[0].popisk)
            assertEquals(itemList[1].popisk, result[1].popisk)
        }
    }

    @Test
    fun `test getItemDetail`() {
        val popisk = "some_popisk"
        val itemDetailDto = ItemDetailDto("1", "popisk1", "description1") // Sample item detail

        // Initialize mocks
        val firebaseDatabase = mock(FirebaseDatabase::class.java)
        val databaseReference = mock(DatabaseReference::class.java)
        val query = mock(Query::class.java)
        val dataSnapshotChild = mock(DataSnapshot::class.java)

        // Set up mock behavior
        `when`(firebaseDatabase.reference).thenReturn(databaseReference)
        `when`(databaseReference.orderByChild(anyString())).thenReturn(query) // Use anyString() matcher
        `when`(query.equalTo(anyString())).thenReturn(query) // Use anyString() matcher
        `when`(dataSnapshotChild.getValue(ItemDetailDto::class.java)).thenReturn(itemDetailDto)

        // Provide firebaseDatabase to your itemRepository
        val itemRepository = ItemRepositoryImpl(firebaseDatabase)

        // Mocking onDataChange callback invocation
        val listenerCaptor = argumentCaptor<ValueEventListener>()
        `when`(databaseReference.addListenerForSingleValueEvent(listenerCaptor.capture())).thenAnswer {
            listenerCaptor.firstValue.onDataChange(dataSnapshot.apply {
                // Mocking dataSnapshot children to return single child
                val childrenIterable = mock(Iterable::class.java) as Iterable<DataSnapshot>
                `when`(childrenIterable.iterator()).thenReturn(listOf(dataSnapshotChild).iterator())
                `when`(getChildren()).thenReturn(childrenIterable)
            })
        }

        // Calling the function under test
        val result = runBlocking { itemRepository.getItemDetail(popisk) }

        // Verify that the result is not null
        assertNotNull(result)

        // Verify that the result is equal to the expected item detail
        assertEquals(itemDetailDto, result)
    }
}

