package space.rozlach.testtaskproject.domain.use_case.get_items

import com.google.firebase.FirebaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import space.rozlach.testtaskproject.core.Resource
import space.rozlach.testtaskproject.domain.model.Item
import space.rozlach.testtaskproject.domain.repository.ItemRepository
import java.io.IOException
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: ItemRepository) {
    operator fun invoke(): Flow<Resource<List<Item>>> = flow {
        try {
            val items = repository.getItemsList().map { it.toItem() }
            if(!items.isNullOrEmpty())
                emit(Resource.Success<List<Item>>(items))
            else
                emit(Resource.Error<List<Item>>("An unexpected error occurred"))
        } catch (e: FirebaseException) {
            emit(Resource.Error<List<Item>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Item>>("Couldn't reach server. Check your internet connection."))
        }
    }
}
