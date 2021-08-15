package ru.malinoil.films.ui.fragment

import java.util.*

data class NoteEntity(
    val id: String,
    val filmId: Int,
    var text: String
) {
    companion object {
        fun getRandomId(): String {
            return UUID.randomUUID().toString()
        }
    }

}