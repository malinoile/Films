package ru.malinoil.films.model

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object Loader {
    private const val DOMAIN = "https://api.themoviedb.org"
    private const val API_KEY = "e47859f88e5b2612abf49f50d6d151cc"

    private const val TOP_RATED_URL = "movie/top_rated"
    private const val NOW_PLAYING_URL = "movie/now_playing"

    var connection: HttpsURLConnection? = null

    fun getResponseString(type: TitleType): String {
        try {
            val url = URL("$DOMAIN/3/${getSubURLForType(type)}?api_key=$API_KEY&language=ru&page=1")
            connection = url.openConnection() as HttpsURLConnection
            connection!!.readTimeout = 10_000
            val reader = BufferedReader(InputStreamReader(connection!!.inputStream))
            return reader.readLines().joinToString()
        } finally {
            connection?.disconnect()
        }
    }

    private fun getSubURLForType(type: TitleType): String {
        return when (type) {
            TitleType.HIGH_RATE -> TOP_RATED_URL
            TitleType.NOW -> NOW_PLAYING_URL
            else -> throw RuntimeException()
        }
    }
}