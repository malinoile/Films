package ru.malinoil.films.model.impls

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.malinoil.films.model.FilmsAPI
import ru.malinoil.films.model.TitleType
import ru.malinoil.films.model.entities.ResultEntity
import ru.malinoil.films.model.repositories.FilmsRepository

private const val TOP_RATED_URL = "top_rated"
private const val NOW_PLAYING_URL = "now_playing"

class NetworkFilmsRepositoryImpl(retrofit: Retrofit) : FilmsRepository {
    private val apiService = retrofit.create(FilmsAPI::class.java)
    override fun getFilmsForType(
        type: TitleType,
        onSuccess: (ResultEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        apiService.getFilmsForType(getSubURLForType(type))
            .enqueue(object : Callback<ResultEntity> {
                override fun onResponse(
                    call: Call<ResultEntity>,
                    response: Response<ResultEntity>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { onSuccess(it) }
                    } else {
                        onError(Throwable("Api code: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                    onError(t)
                }
            })
    }

    private fun getSubURLForType(type: TitleType): String {
        return when (type) {
            TitleType.HIGH_RATE -> TOP_RATED_URL
            TitleType.NOW -> NOW_PLAYING_URL
            else -> throw RuntimeException()
        }
    }

}