package space.rozlach.testtaskproject.presentation.date_parse

import space.rozlach.testtaskproject.domain.use_case.date_parse.ParseDateUseCase
import android.net.ParseException
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import space.rozlach.testtaskproject.domain.use_case.date_parse.ParseDateAndTimeUseCase
import javax.inject.Inject

@HiltViewModel
class DateViewModel @Inject constructor(
    private val parseDateUseCase: ParseDateUseCase,
    private val parseDateAndTime: ParseDateAndTimeUseCase
) : ViewModel() {
    fun parseDate(dateString: String?): String? {
        return try {
            parseDateUseCase.execute(dateString).toString()
        } catch (e: ParseException) {
            // Handle parsing error
            e.printStackTrace()
            null
        }
    }
    fun parseDateAndTime(dateString: String?): String? {
        return try {
            parseDateAndTime.execute(dateString).toString()
        } catch (e: ParseException) {
            // Handle parsing error
            e.printStackTrace()
            null
        }
    }
}