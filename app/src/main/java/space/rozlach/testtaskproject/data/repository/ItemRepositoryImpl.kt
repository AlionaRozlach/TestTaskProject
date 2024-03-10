package space.rozlach.testtaskproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import space.rozlach.testtaskproject.data.remote.dto.ItemDto
import space.rozlach.testtaskproject.domain.repository.ItemRepository

class ItemRepositoryImpl: ItemRepository {

    private val database = FirebaseDatabase.getInstance().reference

    override suspend fun getItemsList(): LiveData<List<ItemDto>> {
        val liveData = MutableLiveData<List<ItemDto>>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<ItemDto>()
                for (childSnapshot in snapshot.children) {
                    val data = childSnapshot.getValue(ItemDto::class.java)
                    data?.let { dataList.add(it) }
                }
                liveData.value = dataList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return liveData
    }
}