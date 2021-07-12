package ru.malinoil.films

data class Film(
        val name: String?,
        val year: Int,
        var rate: Float?,
        val duration: Int?,
        var imageSrc: String?
) {
}