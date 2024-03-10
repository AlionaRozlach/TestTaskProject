package space.rozlach.testtaskproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import space.rozlach.testtaskproject.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val ref = FirebaseDatabase.getInstance().reference
//
//        // Read data from the database
//        ref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Handle data retrieval
//                for (snapshot in dataSnapshot.children) {
//                    val key = snapshot.key
//                    val value = snapshot.value
//                    println("Key: $key, Value: $value")
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle error
//                println("Error: ${databaseError.message}")
//            }
//        })
    }
}