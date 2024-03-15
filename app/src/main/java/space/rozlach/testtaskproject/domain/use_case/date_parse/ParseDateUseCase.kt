package space.rozlach.testtaskproject.domain.use_case.date_parse

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ParseDateUseCase {
    @Throws(ParseException::class)
    fun execute(dateString: String?): String {
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = format.parse(dateString)
        val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return targetFormat.format(date)
    }
}