package ru.malinoil.films.model.entities

import ru.malinoil.films.model.TitleType

data class CategoryEntity(var type: TitleType, var listFilms: List<FilmEntity>)
