package space.rozlach.testtaskproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.rozlach.testtaskproject.data.repository.DateRepositoryImpl
import space.rozlach.testtaskproject.domain.repository.DateRepository
import space.rozlach.testtaskproject.domain.use_case.date_parse.ParseDateAndTimeUseCase
import space.rozlach.testtaskproject.domain.use_case.date_parse.ParseDateUseCase

@Module
@InstallIn(SingletonComponent::class)
object ParsingDateModule {
    @Provides
    fun provideParseDateUseCase(): ParseDateUseCase {
        return ParseDateUseCase()
    }
    @Provides
    fun provideParseDateAndTimeUseCase(): ParseDateAndTimeUseCase {
        return ParseDateAndTimeUseCase()
    }

    @Provides
    fun provideDateRepository(): DateRepository {
        return DateRepositoryImpl()
    }
}