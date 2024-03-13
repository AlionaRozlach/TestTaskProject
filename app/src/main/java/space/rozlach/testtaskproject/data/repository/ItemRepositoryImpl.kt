package space.rozlach.testtaskproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.domain.model.ItemDetail
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
                throw e // Rethrow any exceptions caught during await
            }
        }
    }

    override suspend fun getItemDetail(popisk: String): ItemDetailDto? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<ItemDetailDto>()
            val query: Query = database.orderByChild("popisk").equalTo(popisk)
            var item: ItemDetailDto? = null
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot != null) {
                        for (itemSnapshot in snapshot.children) {
                            // Access each item and do something with it
                            item = itemSnapshot.getValue(ItemDetailDto::class.java)
                            // Process the item
                        }
                    } else {
                        // Handle error
                    }
                    if (item != null)
                        deferred.complete(item!!)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                }
            })

            try {
                deferred.await()
            } catch (e: Exception) {
                throw e // Rethrow any exceptions caught during await
            }
        }
    }
}