package ru.malinoil.films.model.repositories

import ru.malinoil.films.model.entities.ResultEntity
import ru.malinoil.films.model.entities.TitleType

interface FilmsRepository {
    fun getFilmsForType(
        type: TitleType,
        onSuccess: (ResultEntity) -> Unit,
        onError: (Throwable) -> Unit
    )
}