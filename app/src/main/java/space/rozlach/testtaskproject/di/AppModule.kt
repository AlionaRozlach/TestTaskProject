package space.rozlach.testtaskproject.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import space.rozlach.testtaskproject.data.repository.ItemRepositoryImpl
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule{

    @Provides
    @Singleton
    fun provideFirebaseDb(): FirebaseDatabase = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun provideItemRepository(firebaseDb: FirebaseDatabase): ItemRepository {
        return ItemRepositoryImpl(firebaseDb)
    }
}