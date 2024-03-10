package space.rozlach.testtaskproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.*
import space.rozlach.testtaskproject.data.remote.dto.ItemDetailDto
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.domain.model.ItemDetail
import space.rozlach.testtaskproject.domain.repository.ItemRepository

class ItemRepositoryImpl: ItemRepository {

    private val database = FirebaseDatabase.getInstance().reference

    override suspend fun getItemsList():List<ItemDto> {
        val dataList = mutableListOf<ItemDto>()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val data = childSnapshot.getValue(ItemDto::class.java)
                    data?.let { dataList.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return dataList
    }

    override suspend fun getItemDetail(popisk: String): ItemDetailDto? {
        val query: Query = database.orderByChild("popisk").equalTo(popisk)
        val dataSnapshotTask: Task<DataSnapshot> = query.get()

        return try {
            val dataSnapshot = Tasks.await(dataSnapshotTask)
            dataSnapshot.getValue(ItemDetailDto::class.java)
        } catch (e: Exception) {
            // Handle error
            null
        }
    }
}