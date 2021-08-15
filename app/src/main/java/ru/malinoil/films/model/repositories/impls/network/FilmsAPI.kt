package ru.malinoil.films.model.repositories.impls.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.malinoil.films.model.entities.ResultEntity

private const val API_KEY = "e47859f88e5b2612abf49f50d6d151cc"

interface FilmsAPI {
    @GET("3/movie/{type}?api_key=$API_KEY&language=ru&page=1")
    fun getFilmsForType(@Path("type") type: String): Call<ResultEntity>
}