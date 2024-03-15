package space.rozlach.testtaskproject.di

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.rozlach.testtaskproject.data.repository.ItemRepositoryImpl
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import space.rozlach.testtaskproject.domain.use_case.get_item.GetItemUseCase
import space.rozlach.testtaskproject.domain.use_case.get_items.GetItemsUseCase
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

    @Provides
    fun provideGetItemUseCase(itemRepository: ItemRepository): GetItemUseCase {
        return GetItemUseCase(itemRepository)
    }
    @Provides
    fun provideGetItemsUseCase(itemRepository: ItemRepository): GetItemsUseCase {
        return GetItemsUseCase(itemRepository)
    }
}