package space.rozlach.testtaskproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.rozlach.testtaskproject.data.repository.ItemRepositoryImpl
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule{

    @Provides
    @Singleton
    fun provideItemRepository(): ItemRepository {
        return ItemRepositoryImpl()
    }
}