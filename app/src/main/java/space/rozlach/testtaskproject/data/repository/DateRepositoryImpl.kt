package space.rozlach.testtaskproject.data.repository

import space.rozlach.testtaskproject.domain.repository.DateRepository
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

internal class DateRepositoryImpl : DateRepository {
    @Throws(ParseException::class)
    override fun parseDate(dateString: String?): String? {
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = format.parse(dateString)
        val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return targetFormat.format(date)
    }
    @Throws(ParseException::class)
    override fun parseDateAndTime(dateString: String?): String? {
        val format = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val date = format.parse(dateString)
        val targetFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return targetFormat.format(date)
    }
}