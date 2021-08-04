package ru.malinoil.films.model

import org.json.JSONObject
import ru.malinoil.films.model.entities.FilmEntity

object JSONParser {

    fun parseFilms(response: String): MutableList<FilmEntity> {
        val list: MutableList<FilmEntity> = emptyList<FilmEntity>().toMutableList()
        val jsonObj = JSONObject(response)
        val jsonArray = jsonObj.getJSONArray("results")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val film = FilmEntity(
                jsonObject.get("title").toString(),
                jsonObject.get("release_date").toString().split("-")[0].toInt(),
                jsonObject.get("vote_average").toString().toFloat(),
                jsonObject.get("overview").toString(),
                null, TitleType.HIGH_RATE
            )
            film.originalName = jsonObject.get("original_title").toString()
            list.add(film)
        }
        return list
    }

}