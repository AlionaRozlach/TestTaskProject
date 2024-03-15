package space.rozlach.testtaskproject.data.repository

import com.google.firebase.database.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val firebaseDb: FirebaseDatabase,
) : ItemRepository {

    private val database = firebaseDb.reference

    override suspend fun getItemsList(): List<ItemDto> {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<ItemDto>>()

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataList = mutableListOf<ItemDto>()
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(ItemDto::class.java)
                        data?.let { dataList.add(it) }
                    }
                    deferred.complete(dataList)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                }
            })

            try {
                deferred.await()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getItemDetail(popisk: String, position: Int): ItemDetailDto? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<ItemDetailDto?>()
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var index = 0
                    snapshot.children.forEach { itemSnapshot ->
                        val item = itemSnapshot.getValue(ItemDetailDto::class.java)
                        if (item?.popisk == popisk && index == position) {
                            deferred.complete(item)
                            return@forEach
                        }
                        index++
                    }
                    deferred.complete(null)
                }
                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                }
            })
            try {
                deferred.await()
            } catch (e: Exception) {
                throw e
            }
        }
    }
}