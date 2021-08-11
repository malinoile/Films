package ru.malinoil.films.model.repositories

import ru.malinoil.films.model.TitleType
import ru.malinoil.films.model.entities.ResultEntity

interface FilmsRepository {
    fun getFilmsForType(
        type: TitleType,
        onSuccess: (ResultEntity) -> Unit,
        onError: (Throwable) -> Unit
    )
}