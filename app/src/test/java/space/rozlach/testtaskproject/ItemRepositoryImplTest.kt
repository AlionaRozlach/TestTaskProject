package space.rozlach.testtaskproject

import com.google.firebase.database.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.kotlin.argumentCaptor
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.data.repository.ItemRepositoryImpl
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ItemRepositoryImplTest {
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
        Mockito.`when`(firebaseDatabase.reference).thenReturn(databaseReference)
        Mockito.`when`(firebaseDatabase.reference.orderByChild(ArgumentMatchers.any())).thenReturn(query)
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
        val dataSnapshotChild1 = Mockito.mock(DataSnapshot::class.java)
        val dataSnapshotChild2 = Mockito.mock(DataSnapshot::class.java)

        Mockito.`when`(dataSnapshot.children)
            .thenReturn(listOf(dataSnapshotChild1, dataSnapshotChild2))
        Mockito.`when`(dataSnapshotChild1.getValue(ItemDto::class.java)).thenReturn(item1)
        Mockito.`when`(dataSnapshotChild2.getValue(ItemDto::class.java)).thenReturn(item2)

        runBlocking {
            val listenerCaptor = argumentCaptor<ValueEventListener>()
            Mockito.`when`(firebaseDatabase.reference.addListenerForSingleValueEvent(listenerCaptor.capture()))
                .thenAnswer {
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
        val itemDetailDto = ItemDetailDto("1", "popisk1", "description1")

        // Set up mock behavior
        Mockito.`when`(query.equalTo(Mockito.anyString())).thenReturn(query)
        Mockito.`when`(dataSnapshot.getValue(ItemDetailDto::class.java)).thenReturn(itemDetailDto)

        // Provide firebaseDatabase to your itemRepository
        val itemRepository = ItemRepositoryImpl(firebaseDatabase)

        // Mocking onDataChange callback invocation
        val listenerCaptor = argumentCaptor<ValueEventListener>()
        Mockito.`when`(databaseReference.addListenerForSingleValueEvent(listenerCaptor.capture())).thenAnswer {
            listenerCaptor.firstValue.onDataChange(dataSnapshot.apply {
                // Mocking dataSnapshot children to return single child
                val childrenIterable = Mockito.mock(Iterable::class.java) as Iterable<DataSnapshot>
                Mockito.`when`(childrenIterable.iterator()).thenReturn(listOf(dataSnapshot).iterator())
                Mockito.`when`(getChildren()).thenReturn(childrenIterable)
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