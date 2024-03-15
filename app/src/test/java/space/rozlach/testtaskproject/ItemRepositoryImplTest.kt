package space.rozlach.testtaskproject

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.argumentCaptor
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.data.repository.ItemRepositoryImpl
import kotlin.test.assertEquals

class ItemRepositoryImplTest {
    @Mock
    private lateinit var firebaseDatabase: FirebaseDatabase

    @Mock
    private lateinit var databaseReference: DatabaseReference

    @Mock
    private lateinit var query: Query

    @Mock
    private lateinit var dataSnapshot: DataSnapshot

    private lateinit var itemRepository: ItemRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(firebaseDatabase.reference).thenReturn(databaseReference)
        Mockito.`when`(firebaseDatabase.reference.orderByChild(Mockito.anyString())).thenReturn(query)
        itemRepository = ItemRepositoryImpl(firebaseDatabase)
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
}