package ru.malinoil.films.model.entities

data class CategoryEntity(var type: TitleType, var listFilms: List<FilmEntity>)
