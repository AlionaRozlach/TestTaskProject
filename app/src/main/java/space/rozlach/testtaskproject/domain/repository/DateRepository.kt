package space.rozlach.testtaskproject.domain.repository

import java.text.ParseException

interface DateRepository {
    @Throws(ParseException::class)
    fun parseDate(dateString: String?): String?
    @Throws(ParseException::class)
    fun parseDateAndTime(dateString: String?): String?
}