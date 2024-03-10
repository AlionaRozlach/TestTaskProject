package space.rozlach.testtaskproject.domain.use_case.get_item

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.data.remote.dto.toItemDetail
import space.rozlach.testtaskproject.domain.model.Item
import space.rozlach.testtaskproject.domain.model.ItemDetail
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import java.io.IOException
import javax.inject.Inject

class GetItemUseCase @Inject constructor(private val repository: ItemRepository) {

    operator fun invoke(popisk: String): Flow<Resource<ItemDetail>> = flow {
        try {
            emit(Resource.Loading())
            val item = repository.getItemDetail(popisk)?.toItemDetail()
        } catch (e: HttpException) {
            emit(Resource.Error<ItemDetail>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<ItemDetail>("Couldn't reach server. Check your internet connection."))
        }
    }
}
